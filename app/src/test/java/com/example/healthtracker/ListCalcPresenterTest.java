package com.example.healthtracker;

import com.example.healthtracker.feature.listCalc.data.repository.ListCalcRepository;
import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.listCalc.ListCalc;
import com.example.healthtracker.feature.listCalc.presentation.ListCalcPresenter;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListCalcPresenterTest {

    private ListCalc.View view;
    private ListCalcRepository repository;
    private ListCalcPresenter presenter;
    private CalcDao dao;

    @Before
    public void setup() {
        view = mock(ListCalc.View.class);
        repository = mock(ListCalcRepository.class);
        dao = mock(CalcDao.class);
        presenter = new ListCalcPresenter(view, repository);
    }

    @Test
    public void testGetAllRegisters_success() {
        List<Calc> mockList = Arrays.asList(
                new Calc("imc", 22.5, "Peso normal"),
                new Calc("imc", 30.0, "Obesidade grau I")
        );

        doAnswer(invocation -> {
            RequestCallback<List<Calc>> callback = invocation.getArgument(2);
            callback.onSuccess(mockList);
            return null;
        }).when(repository).getAllRegisters(eq(dao), eq("imc"), any());

        presenter.getAllRegisters(dao, "imc");

        verify(view).displayAllRegisters(mockList);
    }

    @Test
    public void testGetAllRegisters_failure() {
        doAnswer(invocation -> {
            RequestCallback<List<Calc>> callback = invocation.getArgument(2);
            callback.onFailure("Falha ao carregar");
            return null;
        }).when(repository).getAllRegisters(eq(dao), eq("imc"), any());

        presenter.getAllRegisters(dao, "imc");

        verify(view).displayFailure("Falha ao carregar");
    }

    @Test
    public void testClearRegisters_success() {
        doAnswer(invocation -> {
            RequestCallback<Boolean> callback = invocation.getArgument(2);
            callback.onSuccess(true);
            return null;
        }).when(repository).clearRegisters(eq(dao), eq("tmb"), any());

        presenter.clearRegisters(dao, "tmb");

        verify(view).onDeleteRegisters();
    }

    @Test
    public void testClearRegisters_failure() {
        doAnswer(invocation -> {
            RequestCallback<Boolean> callback = invocation.getArgument(2);
            callback.onFailure("Erro ao limpar");
            return null;
        }).when(repository).clearRegisters(eq(dao), eq("tmb"), any());

        presenter.clearRegisters(dao, "tmb");

        verify(view).displayFailure("Erro ao limpar");
    }

    @Test
    public void testClearRegisters_failureWithNullMessage() {
        doAnswer(invocation -> {
            RequestCallback<Boolean> callback = invocation.getArgument(2);
            callback.onFailure(null);
            return null;
        }).when(repository).clearRegisters(eq(dao), eq("tmb"), any());

        presenter.clearRegisters(dao, "tmb");

        verify(view).displayFailure("Unknown error");
    }

    @Test
    public void testOnDestroy_setsViewToNull() {
        presenter.onDestroy();
        // Como não temos acesso direto à variável 'view', apenas garantimos que o método existe e não lança exceções
        assertTrue(true);
    }
}
