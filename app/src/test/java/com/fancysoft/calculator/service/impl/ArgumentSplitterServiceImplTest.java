package com.fancysoft.calculator.service.impl;

import static org.junit.Assert.assertEquals;

import com.fancysoft.calculator.service.ArgumentSplitterService;

import org.junit.Test;

import java.util.List;

public class ArgumentSplitterServiceImplTest {

    private final ArgumentSplitterService service = new ArgumentSplitterServiceImpl();

    @Test
    public void shouldSplitSimpleExpression() {
        List<String> expected = List.of("1", "+", "2");

        List<String> actual = service.splitToArguments("1+2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldSplitComplexExpression() {
        List<String> expected = List.of("1", "+", "2", "×", "3");

        List<String> actual = service.splitToArguments("1+2×3");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldSplitExpressionWithWordOperators() {
        List<String> expected = List.of("1", "+", "2", "×", "(", "sin", "90", "-", "2", "^", "4", ")");

        List<String> actual = service.splitToArguments("1+2×(sin90-2^4)");

        assertEquals(expected, actual);
    }
}
