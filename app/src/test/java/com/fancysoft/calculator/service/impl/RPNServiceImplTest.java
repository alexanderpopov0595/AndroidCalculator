package com.fancysoft.calculator.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

import com.fancysoft.calculator.enums.Operation;
import com.fancysoft.calculator.exception.model.AppException;
import com.fancysoft.calculator.service.CalculatorService;
import com.fancysoft.calculator.service.OperationService;
import com.fancysoft.calculator.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RPNServiceImplTest {

    @Mock
    private OperationService opService;
    @Mock
    private CalculatorService calcService;
    @InjectMocks
    private RPNServiceImpl service;

    @Before
    public void init() {
        doAnswer(invocation -> {
            String operation = invocation.getArgument(0);

            for (Operation op : Operation.values()) {
                if (op.isOperation(operation)) {
                    return op;
                }
            }
            throw new AppException(String.format("RPN error: operation %s is unknown", operation));
        }).when(opService).getOperation(anyString());
    }

    @Test
    public void shouldConvertSimpleArithmeticExpression() {
        List<String> expected = List.of("1", "2", "+");

        List<String> actual = service.convertToRPN("1 + 2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertSimpleAlgebraicExpression() {
        List<String> expected = List.of("1", "2", "x");

        List<String> actual = service.convertToRPN("1 x 2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertSimpleExponentExpression() {
        List<String> expected = List.of("1", "2", "^");

        List<String> actual = service.convertToRPN("1 ^ 2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertUnaryExpression() {
        List<String> expected = List.of("8", "%");

        List<String> actual = service.convertToRPN("8 %");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertComplexArithmeticExpression() {
        doAnswer(invocation -> {
            Operation op1 = invocation.getArgument(0);
            Operation op2 = invocation.getArgument(1);

            return op1.ordinal() > op2.ordinal();
        }).when(opService).hasHigherPriority(any(Operation.class), any(Operation.class));

        List<String> expected = List.of("1", "2", "+", "3", "-");

        List<String> actual = service.convertToRPN("1 + 2 - 3");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertComplexAlgebraicExpression() {
        doAnswer(invocation -> {
            Operation op1 = invocation.getArgument(0);
            Operation op2 = invocation.getArgument(1);

            return op1.ordinal() > op2.ordinal();
        }).when(opService).hasHigherPriority(any(Operation.class), any(Operation.class));

        List<String> expected = List.of("1", "2", "÷", "3", "x");

        List<String> actual = service.convertToRPN("1 ÷ 2 x 3");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertComplexExponentExpression() {
        doAnswer(invocation -> {
            Operation op1 = invocation.getArgument(0);
            Operation op2 = invocation.getArgument(1);

            return op1.ordinal() > op2.ordinal();
        }).when(opService).hasHigherPriority(any(Operation.class), any(Operation.class));

        List<String> expected = List.of("2", "1", "^", "4", "√");

        List<String> actual = service.convertToRPN("2 ^ 1 √ 4");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertExpressionWithBrackets() {
        doAnswer(invocation -> {
            Operation op1 = invocation.getArgument(0);
            Operation op2 = invocation.getArgument(1);

            return op1.ordinal() > op2.ordinal();
        }).when(opService).hasHigherPriority(any(Operation.class), any(Operation.class));

        List<String> expected = List.of("1", "2", "3", "4", "5", "^", "-", "x", "+");

        List<String> actual = service.convertToRPN("1 + 2 x ( 3 - 4 ^ 5 )");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertExpressionWithDots() {
        List<String> expected = List.of("1.1", "2.2", "+");

        List<String> actual = service.convertToRPN("1.1 + 2.2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowExceptionWhenOperationIsUnknown() {
        assertThrows(AppException.class, () -> service.convertToRPN("1 ? 2"));
    }

    @Test
    public void shouldResolveSimpleArithmeticRpn() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a + b;
        }).when(calcService).sum(anyDouble(), anyDouble());

        double expected = 3;

        double actual = service.resolveRPN(List.of("1", "2", "+"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveSimpleAlgebraicRpn() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a * b;
        }).when(calcService).multiply(anyDouble(), anyDouble());

        double expected = 6;

        double actual = service.resolveRPN(List.of("2", "3", "x"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveSimpleExponentRpn() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return Math.pow(a, b);
        }).when(calcService).power(anyDouble(), anyDouble());

        double expected = 8;

        double actual = service.resolveRPN(List.of("2", "3", "^"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveUnaryRpn() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            return a / 100;
        }).when(calcService).percent(anyDouble());

        double expected = 0.08;

        double actual = service.resolveRPN(List.of("8", "%"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveComplexArithmeticRpn() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a + b;
        }).when(calcService).sum(anyDouble(), anyDouble());

        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a - b;
        }).when(calcService).subtract(anyDouble(), anyDouble());

        double expected = 0;

        double actual = service.resolveRPN(List.of("1", "2", "+", "3", "-"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveComplexAlgebraicRpn() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a * b;
        }).when(calcService).multiply(anyDouble(), anyDouble());

        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a / b;
        }).when(calcService).divide(anyDouble(), anyDouble());

        double expected = 1.5;

        double actual = service.resolveRPN(List.of("1", "2", "÷", "3", "x"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveComplexExponentRpn() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return Math.pow(a, b);
        }).when(calcService).power(anyDouble(), anyDouble());

        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            double power = Math.pow(a, 1.0 / b);
            return (double) Math.round(power);
        }).when(calcService).root(anyDouble(), anyDouble());
        double expected = 2;

        double actual = service.resolveRPN(List.of("2", "1", "^", "4", "√"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveComplexMixedRpn() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a + b;
        }).when(calcService).sum(anyDouble(), anyDouble());

        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a * b;
        }).when(calcService).multiply(anyDouble(), anyDouble());

        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return Math.pow(a, b);
        }).when(calcService).power(anyDouble(), anyDouble());

        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a - b;
        }).when(calcService).subtract(anyDouble(), anyDouble());

        double expected = -9.0;

        double actual = service.resolveRPN(List.of("1", "2", "3", "2", "3", "^", "-", "x", "+"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveRpnWithBrackets() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a + b;
        }).when(calcService).sum(anyDouble(), anyDouble());

        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a - b;
        }).when(calcService).subtract(anyDouble(), anyDouble());

        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a * b;
        }).when(calcService).multiply(anyDouble(), anyDouble());

        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a / b;
        }).when(calcService).divide(anyDouble(), anyDouble());
        double expected = 5.4;

        double actual = service.resolveRPN(List.of("1", "2", "3", "4", "5", "÷", "-", "x", "+"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveExpressionWithDots() {
        doAnswer(invocation -> {
            double a = (double) invocation.getArguments()[0];
            double b = (double) invocation.getArguments()[1];
            return a + b;
        }).when(calcService).sum(anyDouble(), anyDouble());

        double expected = 3.3;

        double actual = service.resolveRPN(List.of("1.1", "2.2", "+"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldThrowExceptionWhenCalculatorOperationIsUnknown() {
        assertThrows(AppException.class, () -> service.resolveRPN(List.of("2", "2", "?")));
    }
}
