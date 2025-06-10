package com.example.healthtracker;

import static org.mockito.Mockito.*;

import android.content.res.Resources;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.heartRate.presentation.HeartRatePresenter;
import com.example.healthtracker.feature.heartRate.HeartRate;
import com.example.healthtracker.feature.heartRate.data.repository.HeartRateRepository;
import com.example.healthtracker.model.CalcDao;

import kotlin.Pair;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

public class HeartRatePresenterTest {

    private HeartRate.View view;
    private HeartRateRepository repository;
    private HeartRatePresenter presenter;

    @Before
    public void setUp() {
        view = mock(HeartRate.View.class);
        repository = mock(HeartRateRepository.class);
        presenter = new HeartRatePresenter(view, repository);
    }

    @Test
    public void testValidate_validInput_returnsTrue() {
        boolean isValid = presenter.validate("25", "75");
        assert isValid;
    }

    @Test
    public void testValidate_invalidInput_returnsFalse() {
        assert !presenter.validate("0", "75");
        assert !presenter.validate("25", "0");
        assert !presenter.validate("", "75");
        assert !presenter.validate("25", "");
    }

    @Test
    public void testRegisterHeartRateValue_success() {
        CalcDao dao = mock(CalcDao.class);

        doAnswer(invocation -> {
            RequestCallback<Boolean> callback = invocation.getArgument(2);
            callback.onSuccess(true);
            return null;
        }).when(repository).registerHeartRateValue(anyDouble(), anyString(), ArgumentMatchers.<RequestCallback<Boolean>>any());

        presenter.registerHeartRateValue(72.0, "Normal");
        verify(view).onRegisterHeartRate();
    }

    @Test
    public void testRegisterHeartRateValue_failure() {
        CalcDao dao = mock(CalcDao.class);

        doAnswer(invocation -> {
            RequestCallback<Boolean> callback = invocation.getArgument(2);
            callback.onFailure("Something went wrong");
            return null;
        }).when(repository).registerHeartRateValue(anyDouble(), anyString(), ArgumentMatchers.<RequestCallback<Boolean>>any());

        presenter.registerHeartRateValue(72.0, "Normal");
        verify(view).displayFailure("Something went wrong");
    }

    @Test
    public void testGetClassificationHeartRate_male() {
        // Idade 30, BPM 65, vai ser "Normal" para homens de idade 26–35
        Pair<Integer, Pair<Integer, Integer>> result = presenter.getClassificationHeartRate(true, 65, 30);
        assert result.getSecond().getFirst() <= 65 && result.getSecond().getSecond() >= 65;
    }

    @Test
    public void testGetClassificationHeartRate_female() {
        // Idade 30, BPM 65, vai ser  "Normal" para mulheres de idade 26–35
        Pair<Integer, Pair<Integer, Integer>> result = presenter.getClassificationHeartRate(false, 65, 30);
        assert result.getSecond().getFirst() <= 65 && result.getSecond().getSecond() >= 65;
    }

    @Test
    public void testOnDestroy_setsViewNull() throws Exception {
        presenter.onDestroy();
        // Não é possivel testar diretamente, porém se não houver nenhum registro após deletar é possível o teste.
        presenter.registerHeartRateValue(70, "Normal");
        // No exception = passou
    }
}
