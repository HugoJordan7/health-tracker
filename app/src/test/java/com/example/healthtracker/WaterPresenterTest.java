package com.example.healthtracker;

import com.example.healthtracker.feature.water.Water;
import com.example.healthtracker.feature.water.presentation.WaterPresenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WaterPresenterTest {

    private Water.View mockView;
    private WaterPresenter presenter;

    @Before
    public void setUp() {
        mockView = mock(Water.View.class);
        presenter = new WaterPresenter(mockView);
    }

    @Test
    public void testValidate_validInput_returnsTrue() {
        assertTrue(presenter.validate("70", "25"));
    }

    @Test
    public void testValidate_invalidWeight_returnsFalse() {
        assertFalse(presenter.validate("", "25"));
        assertFalse(presenter.validate("0", "25"));
    }

    @Test
    public void testValidate_invalidAge_returnsFalse() {
        assertFalse(presenter.validate("70", ""));
        assertFalse(presenter.validate("70", "0"));
    }

    @Test
    public void testCalculateIdealQuantityWater_childrenAndTeens() {
        assertEquals(40 * 30, presenter.calculateIdealQuantityWater(10, 30));
    }

    @Test
    public void testCalculateIdealQuantityWater_adults() {
        assertEquals(35 * 70, presenter.calculateIdealQuantityWater(25, 70));
    }

    @Test
    public void testCalculateIdealQuantityWater_middleAged() {
        assertEquals(30 * 65, presenter.calculateIdealQuantityWater(60, 65));
    }

    @Test
    public void testCalculateIdealQuantityWater_older() {
        assertEquals(25 * 80, presenter.calculateIdealQuantityWater(70, 80));
    }

    @Test
    public void testQuantityByExercise_levels() {
        String[] array = {"Nenhum", "Leve", "Moderado", "Intenso", "Muito intenso"};

        assertEquals(0, presenter.quantityByExercise("Nenhum", array));
        assertEquals(325, presenter.quantityByExercise("Leve", array));
        assertEquals(750, presenter.quantityByExercise("Moderado", array));
        assertEquals(1075, presenter.quantityByExercise("Intenso", array));
        assertEquals(1500, presenter.quantityByExercise("Muito intenso", array));
        assertEquals(2250, presenter.quantityByExercise("Outro", array));
    }

    @Test
    public void testOnDestroy_setsViewToNull() {
        presenter.onDestroy();
        assertNull(getPrivateViewField(presenter));
    }

    // Utilitário para acessar o campo privado `view`
    private Water.View getPrivateViewField(WaterPresenter presenter) {
        try {
            java.lang.reflect.Field viewField = WaterPresenter.class.getDeclaredField("view");
            viewField.setAccessible(true);
            return (Water.View) viewField.get(presenter);
        } catch (Exception e) {
            return null;
        }
    }
}
