package com.fancysoft.calculator.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.fancysoft.calculator.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandTransformerImplTest {

    @Mock
    private Context context;
    @InjectMocks
    private CommandTransformerImpl transformer;

    @Before
    public void setUp() {
        when(context.getString(R.string.square_pow)).thenReturn("x2");
        when(context.getString(R.string.pow)).thenReturn("^");
        when(context.getString(R.string.root)).thenReturn("√");
        transformer.init();
    }

    @Test
    public void shouldTransformCommand() {
        String expected = "^2";

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
