package com.fancysoft.calculator.model;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fancysoft.calculator.model.screen.Screen;
import com.fancysoft.calculator.service.CommandTransformer;
import com.fancysoft.calculator.service.RPNService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {

    @Mock
    private Screen screen;
    @Mock
    private CommandTransformer transformer;
    @Mock
    private RPNService service;
    @InjectMocks
    private Calculator calculator;

    @Test
    public void shouldDisplayCommand() {
        when(transformer.transform(anyString())).thenReturn("transformed command");

        calculator.display("command");

        verify(transformer).transform(anyString());
        verify(screen).display(anyString());
    }

    @Test
    public void shouldExecute() {
        when(screen.getExpression()).thenReturn("expression");
        when(service.convertToRPN("expression")).thenReturn(List.of("r", "p", "n"));
        when(service.resolveRPN(List.of("r", "p", "n"))).thenReturn(1.0);

        calculator.execute();

        verify(screen).getExpression();
        verify(service).convertToRPN("expression");
        verify(service).resolveRPN(List.of("r", "p", "n"));
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
