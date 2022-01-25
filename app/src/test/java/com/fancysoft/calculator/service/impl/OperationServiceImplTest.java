package com.fancysoft.calculator.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fancysoft.calculator.enums.Operation;
import com.fancysoft.calculator.service.OperationService;

import org.junit.Test;

public class OperationServiceImplTest {

    private final OperationService service = new OperationServiceImpl();

    @Test
    public void shouldReturnSpaceOperationGroup() {
        Operation expected = Operation.SPACE;

        Operation actual = service.getOperationGroup(' ');

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnCloseBracketOperationGroup() {
        Operation expected = Operation.CLOSE_BRACKET;

        Operation actual = service.getOperationGroup(')');

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnDigitOperationGroup() {
        Operation expected = Operation.DIGIT;

        Operation actual = service.getOperationGroup('3');

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnOpenBracketOperationGroup() {
        Operation expected = Operation.OPEN_BRACKET;

        Operation actual = service.getOperationGroup('(');

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnArithmeticOperationGroup() {
        Operation expected = Operation.ARITHMETIC;

        Operation actual = service.getOperationGroup('+');

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnAlgebraicOperationGroup() {
        Operation expected = Operation.ALGEBRAIC;

        Operation actual = service.getOperationGroup('x');

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnTrueWhenOperationHasHigherPriority() {
        assertTrue(service.hasHigherPriority(Operation.ALGEBRAIC, Operation.ARITHMETIC));
    }

    @Test
    public void shouldReturnFalseWhenOperationHasHigherPriority() {
        assertFalse(service.hasHigherPriority(Operation.DIGIT, Operation.ARITHMETIC));
    }
}
