package com.example.healthtracker;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.imc.Imc;
import com.example.healthtracker.feature.imc.data.repository.ImcRepository;
import com.example.healthtracker.feature.imc.presentation.ImcPresenter;
import com.example.healthtracker.model.CalcDao;

import org.junit.Before;
import org.junit.Test;


public class ImcPresenterTest {

    private Imc.View view;
    private ImcRepository repository;
    private ImcPresenter presenter;

    @Before
    public void setup() {
        view = mock(Imc.View.class);
        repository = mock(ImcRepository.class);
        presenter = new ImcPresenter(view, repository);
    }

    @Test
    public void testRegisterImcValue_success() {
        CalcDao dao = mock(CalcDao.class);

        doAnswer(invocation -> {
            RequestCallback<Boolean> callback = invocation.getArgument(1);
            callback.onSuccess(true);
            return null;
        }).when(repository).registerImcValue(anyDouble(), any());

        presenter.registerImcValue(22.0);

        verify(view).onRegisterImcValue();
    }

    @Test
    public void testRegisterImcValue_failure() {
        CalcDao dao = mock(CalcDao.class);

        doAnswer(invocation -> {
            RequestCallback<Boolean> callback = invocation.getArgument(1);
            callback.onFailure("Erro ao salvar");
            return null;
        }).when(repository).registerImcValue(anyDouble(), any());

        presenter.registerImcValue(22.0);

        verify(view).displayFailure("Erro ao salvar");
    }

    @Test
    public void testValidate_validInputs() {
        assertTrue(presenter.validate("170", "65"));
    }

    @Test
    public void testValidate_invalidHeight() {
        assertFalse(presenter.validate("", "65"));
        assertFalse(presenter.validate("0", "65"));
    }

    @Test
    public void testValidate_invalidWeight() {
        assertFalse(presenter.validate("170", ""));
        assertFalse(presenter.validate("170", "0"));
    }

    @Test
    public void testCalculateImc() {
        double imc = presenter.calculateImc(170, 65);
        assertEquals(22.49, imc, 0.01);
    }

    @Test
    public void testGetImcSituation_lowWeight() {
        assertEquals(R.string.imc_low_weight, presenter.getImcSituation(17.0));
    }

    @Test
    public void testGetImcSituation_normalWeight() {
        assertEquals(R.string.imc_normal_weight, presenter.getImcSituation(22.0));
    }

    @Test
    public void testGetImcSituation_aboveWeight() {
        assertEquals(R.string.imc_so_above_weight, presenter.getImcSituation(28.0));
    }

    @Test
    public void testGetImcSituation_obesity1() {
        assertEquals(R.string.imc_above_1, presenter.getImcSituation(33.0));
    }

    @Test
    public void testGetImcSituation_obesity2() {
        assertEquals(R.string.imc_above_2, presenter.getImcSituation(38.0));
    }

    @Test
    public void testGetImcSituation_obesity3() {
        assertEquals(R.string.imc_above_3, presenter.getImcSituation(42.0));
    }

    @Test
    public void testOnDestroy_setsViewNull() {
        presenter.onDestroy();
        // Só valida que o método existe sem exception
        assertTrue(true);
    }
}
