package com.fancysoft.calculator.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.fancysoft.calculator.exception.model.AppException;
import com.fancysoft.calculator.service.CalculatorService;
import com.fancysoft.calculator.utils.Constants;

import org.junit.Test;

public class CalculatorServiceImplTest {

    private final CalculatorService service = new CalculatorServiceImpl();

    @Test
    public void shouldPerformAddition() {
        double expected = 3.0;

        double actual = service.sum(1.0, 2.0);

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldPerformSubtraction() {
        double expected = -1.0;

        double actual = service.subtract(1.0, 2.0);

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldPerformMultiplication() {
        double expected = 6.0;

        double actual = service.multiply(2.0, 3.0);

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldPerformDivision() {
        double expected = 2.0;

        double actual = service.divide(6.0, 3.0);

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldThrowExceptionWhenDividingByZero() {
        String expected = "Zero division";

        AppException exception = assertThrows(AppException.class, () -> service.divide(3.0,0.0));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void shouldPerformExponentiation() {
        double expected = 8.0;

        double actual = service.power(2.0, 3.0);

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldPerformRootOperationWithRoundPowerValues() {
        double expected = 2.0;

        double actual = service.root(8.0, 3.0);

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldPerformRootOperationWithNonRoundPowerValues() {
        double expected = 3.0;

        double actual = service.root(125.0, 5.0);

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldPerformPercentOperation() {
        double expected = 0.05;

        double actual = service.percent(5);

        assertEquals(expected, actual, Constants.DELTA);
    }
}
