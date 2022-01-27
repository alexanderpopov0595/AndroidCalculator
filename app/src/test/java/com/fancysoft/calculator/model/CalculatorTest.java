package com.fancysoft.calculator.model;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fancysoft.calculator.service.RPNService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {

    @Mock
    private Screen screen;
    @Mock
    private RPNService service;
    @InjectMocks
    private Calculator calculator;

    @Test
    public void shouldDisplayCommand() {
        calculator.display("command");

        verify(screen).display(anyString());
    }

    @Test
    public void shouldExecute() {
        when(screen.getExpression()).thenReturn("expression");
        when(service.convertToRPN("expression")).thenReturn("rpn");
        when(service.resolveRPN("rpn")).thenReturn(1.0);

        calculator.execute();

        verify(screen).getExpression();
        verify(service).convertToRPN("expression");
        verify(service).resolveRPN("rpn");
        verify(screen).display(1.0);
    }

    @Test
    public void shouldClear() {
        calculator.clear();

        verify(screen).clear();
    }

    @Test
    public void shouldBackspace() {
        calculator.backspace();

        verify(screen).backspace();
    }
}
