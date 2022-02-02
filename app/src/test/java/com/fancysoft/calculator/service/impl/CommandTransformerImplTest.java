package com.fancysoft.calculator.service.impl;

import static org.junit.Assert.assertEquals;

import com.fancysoft.calculator.service.CommandTransformer;

import org.junit.Test;

public class CommandTransformerImplTest {

    private final CommandTransformer transformer = new CommandTransformerImpl();

    @Test
    public void shouldTransformCommand() {
        String expected = " ^ 2 ";

        String actual = transformer.transform("x2");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnOriginalCommand() {
        String expected = "sin";

        String actual = transformer.transform("sin");

        assertEquals(expected, actual);
    }
}
