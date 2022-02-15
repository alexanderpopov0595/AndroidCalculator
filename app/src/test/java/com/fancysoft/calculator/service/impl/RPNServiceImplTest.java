package com.fancysoft.calculator.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import com.fancysoft.calculator.exception.model.AppException;
import com.fancysoft.calculator.rpn.ArgumentType;
import com.fancysoft.calculator.rpn.operation.Operation;
import com.fancysoft.calculator.service.ArgumentService;
import com.fancysoft.calculator.service.ArgumentSplitterService;
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
    private ArgumentSplitterService splitterService;
    @Mock
    private ArgumentService argService;
    @InjectMocks
    private RPNServiceImpl service;

    @Before
    public void init() {
        doAnswer(invocation -> {
            String value = invocation.getArgument(0);

            for (ArgumentType op : ArgumentType.values()) {
                if (op.isOperation(value)) {
                    return op;
                }
            }
            throw new AppException(String.format("RPN error: argument type %s is unknown", value));
        }).when(argService).getArgumentType(anyString());

        doAnswer(invocation -> {
            ArgumentType type = invocation.getArgument(0);
            String value = invocation.getArgument(1);

            for (Operation operation : type.getOperations()) {
                if (operation.isOperation(value)) {
                    return operation;
                }
            }
            throw new AppException(String.format("RPN error: operation %s is unknown", value));
        }).when(argService).getOperation(any(ArgumentType.class), anyString());
    }

    @Test
    public void shouldConvertSimpleArithmeticExpression() {
        when(splitterService.splitToArguments("1+2")).thenReturn(List.of("1", "+", "2"));

        List<String> expected = List.of("1", "2", "+");

        List<String> actual = service.convertToRPN("1+2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertSimpleAlgebraicExpression() {
        when(splitterService.splitToArguments("1×2")).thenReturn(List.of("1", "×", "2"));

        List<String> expected = List.of("1", "2", "×");

        List<String> actual = service.convertToRPN("1×2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertSimpleExponentExpression() {
        when(splitterService.splitToArguments("1^2")).thenReturn(List.of("1", "^", "2"));

        List<String> expected = List.of("1", "2", "^");

        List<String> actual = service.convertToRPN("1^2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertUnaryExpression() {
        when(splitterService.splitToArguments("8%")).thenReturn(List.of("8", "%"));

        List<String> expected = List.of("8", "%");

        List<String> actual = service.convertToRPN("8%");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertComplexArithmeticExpression() {
        when(splitterService.splitToArguments("1+2-3")).thenReturn(List.of("1", "+", "2", "-", "3"));

        List<String> expected = List.of("1", "2", "+", "3", "-");

        List<String> actual = service.convertToRPN("1+2-3");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertComplexAlgebraicExpression() {
        when(splitterService.splitToArguments("1÷2×3")).thenReturn(List.of("1", "÷", "2", "×", "3"));

        List<String> expected = List.of("1", "2", "÷", "3", "×");

        List<String> actual = service.convertToRPN("1÷2×3");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertComplexExponentExpression() {
        when(splitterService.splitToArguments("2^1√4")).thenReturn(List.of("2", "^", "1", "√", "4"));

        List<String> expected = List.of("2", "1", "^", "4", "√");

        List<String> actual = service.convertToRPN("2^1√4");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertExpressionWithBrackets() {
        when(splitterService.splitToArguments("1+2×(3-4^5)"))
                .thenReturn(List.of("1", "+", "2", "×", "(", "3", "-", "4", "^", "5", ")"));

        List<String> expected = List.of("1", "2", "3", "4", "5", "^", "-", "×", "+");

        List<String> actual = service.convertToRPN("1+2×(3-4^5)");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldConvertExpressionWithDots() {
        when(splitterService.splitToArguments("1.1+2.2")).thenReturn(List.of("1.1", "+", "2.2"));

        List<String> expected = List.of("1.1", "2.2", "+");

        List<String> actual = service.convertToRPN("1.1+2.2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowExceptionWhenOperationIsUnknown() {
        when(splitterService.splitToArguments("1?2"))
                .thenReturn(List.of("1", "?", "2"));
        assertThrows(AppException.class, () -> service.convertToRPN("1?2"));
    }

    @Test
    public void shouldResolveSimpleArithmeticRpn() {
        double expected = 3;

        double actual = service.resolveRPN(List.of("1", "2", "+"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveSimpleAlgebraicRpn() {
        double expected = 6;

        double actual = service.resolveRPN(List.of("2", "3", "×"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveSimpleExponentRpn() {
        double expected = 8;

        double actual = service.resolveRPN(List.of("2", "3", "^"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveUnaryRpn() {
        double expected = 0.08;

        double actual = service.resolveRPN(List.of("8", "%"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveComplexArithmeticRpn() {
        double expected = 0;

        double actual = service.resolveRPN(List.of("1", "2", "+", "3", "-"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveComplexAlgebraicRpn() {
        double expected = 1.5;

        double actual = service.resolveRPN(List.of("1", "2", "÷", "3", "×"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveComplexExponentRpn() {
        double expected = 2;

        double actual = service.resolveRPN(List.of("2", "1", "^", "4", "√"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveComplexMixedRpn() {
        double expected = -9.0;

        double actual = service.resolveRPN(List.of("1", "2", "3", "2", "3", "^", "-", "×", "+"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveRpnWithBrackets() {
        double expected = 5.4;

        double actual = service.resolveRPN(List.of("1", "2", "3", "4", "5", "÷", "-", "×", "+"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldResolveExpressionWithDots() {
        double expected = 3.3;

        double actual = service.resolveRPN(List.of("1.1", "2.2", "+"));

        assertEquals(expected, actual, Constants.DELTA);
    }

    @Test
    public void shouldThrowExceptionWhenCalculatorOperationIsUnknown() {
        assertThrows(AppException.class, () -> service.resolveRPN(List.of("2", "2", "?")));
    }
}
