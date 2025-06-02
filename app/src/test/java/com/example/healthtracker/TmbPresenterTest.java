package com.example.healthtracker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.feature.tmb.Tmb;
import com.example.healthtracker.feature.tmb.data.repository.TmbRepository;
import com.example.healthtracker.feature.tmb.presentation.TmbPresenter;
import com.example.healthtracker.model.CalcDao;

import org.junit.Before;
import org.junit.Test;


public class TmbPresenterTest {

    private Tmb.View mockView;
    private TmbRepository mockRepository;
    private CalcDao mockDao;
    private TmbPresenter presenter;

    @Before
    public void setUp() {
        mockView = mock(Tmb.View.class);
        mockRepository = mock(TmbRepository.class);
        mockDao = mock(CalcDao.class);
        presenter = new TmbPresenter(mockView, mockRepository);
    }

    @Test
    public void testValidate_validInputs_returnsTrue() {
        assertTrue(presenter.validate("170", "70", "25"));
    }

    @Test
    public void testValidate_invalidInputs_returnsFalse() {
        assertFalse(presenter.validate("0", "70", "25"));
        assertFalse(presenter.validate("170", "", "25"));
        assertFalse(presenter.validate("170", "70", "0"));
    }

    @Test
    public void testCalculateTmb_forMan() {
        double result = presenter.calculateTmb(true, 180, 75, 25);
        // 66 + (13.7 * 75) + (5 * 180) - (6.8 * 25) = 66 + 1027.5 + 900 - 170 = 1823.5
        assertEquals(1823.5, result, 0.1);
    }

    @Test
    public void testCalculateTmb_forWoman() {
        double result = presenter.calculateTmb(false, 160, 60, 30);
        // 655 + (9.6 * 60) + (1.8 * 160) - (4.7 * 30) = 655 + 576 + 288 - 141 = 1378
        assertEquals(1378.0, result, 0.1);
    }

    @Test
    public void testTmbAdaptedForLifestyle() {
        String[] lifestyles = {"Sedentário", "Levemente ativo", "Moderadamente ativo", "Muito ativo", "Extremamente ativo"};

        assertEquals(1200 * 1.2, presenter.tmbAdaptedForLifestyle("Sedentário", 1200, lifestyles), 0.1);
        assertEquals(1200 * 1.375, presenter.tmbAdaptedForLifestyle("Levemente ativo", 1200, lifestyles), 0.1);
        assertEquals(1200 * 1.55, presenter.tmbAdaptedForLifestyle("Moderadamente ativo", 1200, lifestyles), 0.1);
        assertEquals(1200 * 1.725, presenter.tmbAdaptedForLifestyle("Muito ativo", 1200, lifestyles), 0.1);
        assertEquals(1200 * 1.9, presenter.tmbAdaptedForLifestyle("Extremamente ativo", 1200, lifestyles), 0.1);
        assertEquals(0.0, presenter.tmbAdaptedForLifestyle("Inexistente", 1200, lifestyles), 0.1);
    }

    @Test
    public void testRegisterTmbValue_success() {
        double tmbValue = 1800.0;

        doAnswer(invocation -> {
            RequestCallback<Boolean> callback = invocation.getArgument(2);
            callback.onSuccess(true);
            return null;
        }).when(mockRepository).registerTmbValue(eq(tmbValue), eq(mockDao), any());

        presenter.registerTmbValue(tmbValue, mockDao);
        verify(mockView).onRegisterTmbValue();
    }

    @Test
    public void testRegisterTmbValue_failure() {
        double tmbValue = 1800.0;

        doAnswer(invocation -> {
            RequestCallback<Boolean> callback = invocation.getArgument(2);
            callback.onFailure("Erro");
            return null;
        }).when(mockRepository).registerTmbValue(eq(tmbValue), eq(mockDao), any());

        presenter.registerTmbValue(tmbValue, mockDao);
        verify(mockView).displayFailure("Erro");
    }

    @Test
    public void testOnDestroy() {
        presenter.onDestroy();
        assertNull(getPrivateViewField(presenter));
    }

    // Utilitário para acessar o campo privado `view`
    private Tmb.View getPrivateViewField(TmbPresenter presenter) {
        try {
            java.lang.reflect.Field viewField = TmbPresenter.class.getDeclaredField("view");
            viewField.setAccessible(true);
            return (Tmb.View) viewField.get(presenter);
        } catch (Exception e) {
            return null;
        }
    }
}
