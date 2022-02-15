package com.fancysoft.calculator.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.fancysoft.calculator.exception.model.AppException;
import com.fancysoft.calculator.rpn.ArgumentType;
import com.fancysoft.calculator.rpn.operation.Operation;
import com.fancysoft.calculator.rpn.operation.enums.Arithmetic;
import com.fancysoft.calculator.service.ArgumentService;

import org.junit.Test;

public class ArgumentServiceImplTest {

    private final ArgumentService service = new ArgumentServiceImpl();

    @Test
    public void shouldReturnCloseBracketOperationGroup() {
        ArgumentType expected = ArgumentType.CLOSE_BRACKET;

        ArgumentType actual = service.getArgumentType(")");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnDigitOperationGroup() {
        ArgumentType expected = ArgumentType.DIGIT;

        ArgumentType actual = service.getArgumentType("3");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnOpenBracketOperationGroup() {
        ArgumentType expected = ArgumentType.OPEN_BRACKET;

        ArgumentType actual = service.getArgumentType("(");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnArithmeticOperationGroup() {
        ArgumentType expected = ArgumentType.ARITHMETIC;

        ArgumentType actual = service.getArgumentType("+");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnAlgebraicOperationGroup() {
        ArgumentType expected = ArgumentType.ALGEBRAIC;

        ArgumentType actual = service.getArgumentType("×");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnOperation() {
        Operation expected = Arithmetic.SUM;

        Operation actual = service.getOperation(ArgumentType.ARITHMETIC, "+");

        assertEquals(expected, actual);
    }


    @Test
    public void shouldThrowExceptionWhenArgumentTypeIsUnknown() {
        AppException actual = assertThrows(AppException.class, () -> service.getArgumentType("ln"));

        assertEquals("RPN error: argument type ln is unknown", actual.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenOperationIsUnknown() {
        AppException actual = assertThrows(AppException.class, () -> service.getOperation(ArgumentType.UNARY, "ln"));

        assertEquals("RPN error: operation ln is unknown", actual.getMessage());
    }
}
