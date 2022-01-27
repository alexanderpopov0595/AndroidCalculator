package com.fancysoft.calculator.model;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import android.widget.TextView;

import com.fancysoft.calculator.enums.ScreenMode;
import com.fancysoft.calculator.utils.Constants;
import com.fancysoft.calculator.utils.Helper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

@RunWith(MockitoJUnitRunner.class)
public class ScreenTest {

    @Mock
    private TextView view;
    @InjectMocks
    private Screen screen;

    @Before
    public void init() {
        StringBuilder expression = new StringBuilder(Constants.SCREEN_DEFAULT_VALUE);

        when(view.getText()).thenReturn(expression);

        doAnswer(invocation -> {
            expression.setLength(0);
            expression.append(invocation.getArguments()[0]);
            return null;
        }).when(view).setText(anyString());

        doAnswer(invocation -> expression.append(invocation.getArguments()[0])).when(view).append(anyString());
    }

    @Test
    public void shouldDisplayCommandWhenExpressionWasNotEntered() {
        String expectedExpression = "1";
        ScreenMode expectedScreenMode = ScreenMode.CONTINUE;

        screen.display("1");

        ScreenMode actualScreenMode = getField("mode", ScreenMode.class);
        String actualExpression = getField("view", TextView.class).getText().toString();

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    @Test
    public void shouldDisplayCommandWhenExpressionWasEntered() {
        setField("mode", ScreenMode.CONTINUE);
        getField("view", TextView.class).setText("1");

        String expectedExpression = "12";
        ScreenMode expectedScreenMode = ScreenMode.CONTINUE;

        screen.display("2");

        ScreenMode actualScreenMode = getField("mode", ScreenMode.class);
        String actualExpression = getField("view", TextView.class).getText().toString();

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    @Test
    public void shouldReturnDefaultValueAfterClear() {
        setField("mode", ScreenMode.CONTINUE);
        getField("view", TextView.class).setText("123");

        String expectedExpression = "0";
        ScreenMode expectedScreenMode = ScreenMode.START;

        screen.clear();

        ScreenMode actualScreenMode = getField("mode", ScreenMode.class);
        String actualExpression = getField("view", TextView.class).getText().toString();

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    @Test
    public void shouldReturnResultWithDecimalPart() {
        setField("mode", ScreenMode.CONTINUE);

        String expectedExpression = "2.2";
        ScreenMode expectedScreenMode = ScreenMode.START;

        screen.display(2.2);

        String actualExpression = getField("view", TextView.class).getText().toString();
        ScreenMode actualScreenMode = getField("mode", ScreenMode.class);

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    @Test
    public void shouldReturnResultWithoutDecimalPart() {
        setField("mode", ScreenMode.CONTINUE);

        String expectedExpression = "2";
        ScreenMode expectedScreenMode = ScreenMode.START;

        screen.display(2.0);

        String actualExpression = getField("view", TextView.class).getText().toString();
        ScreenMode actualScreenMode = getField("mode", ScreenMode.class);

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    @Test
    public void shouldBackspaceExpressionWhenExpressionIsEntered() {
        getField("view", TextView.class).setText("12");
        setField("mode", ScreenMode.CONTINUE);

        String expectedExpression = "1";
        ScreenMode expectedScreenMode = ScreenMode.CONTINUE;

        screen.backspace();

        ScreenMode actualScreenMode = getField("mode", ScreenMode.class);
        String actualExpression = getField("view", TextView.class).getText().toString();

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    @Test
    public void shouldBackspaceExpressionWhenSingleNumberExpressionIsEntered() {
        getField("view", TextView.class).setText("1");
        setField("mode", ScreenMode.CONTINUE);

        String expectedExpression = "0";
        ScreenMode expectedScreenMode = ScreenMode.START;

        screen.backspace();

        ScreenMode actualScreenMode = getField("mode", ScreenMode.class);
        String actualExpression = getField("view", TextView.class).getText().toString();

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    @Test
    public void shouldBackspaceExpressionWhenNoExpressionIsEntered() {
        String expectedExpression = "0";
        ScreenMode expectedScreenMode = ScreenMode.START;

        screen.backspace();

        ScreenMode actualScreenMode = getField("mode", ScreenMode.class);
        String actualExpression = getField("view", TextView.class).getText().toString();

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    @Test
    public void shouldReturnDefaultValueWhenNoExpressionIsEntered() {
        String expected = "0";

        String actual = screen.getExpression();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnExpressionWhenSomeExpressionIsEntered() {
        getField("view", TextView.class).setText("12");

        String expected = "12";

        String actual = screen.getExpression();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnScreenState() {
        setField("mode", ScreenMode.CONTINUE);
        getField("view", TextView.class).setText("123");

        String expectedExpression = "123";
        ScreenMode expectedScreenMode = ScreenMode.CONTINUE;

        ScreenState state = screen.getScreenState();

        String actualExpression = state.getExpression();
        ScreenMode actualScreenMode = state.getMode();

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    @Test
    public void shouldSetScreenState() {
        String expectedExpression = "123";
        ScreenMode expectedScreenMode = ScreenMode.CONTINUE;

        ScreenState state = new ScreenState("123", ScreenMode.CONTINUE);

        screen.setScreenState(state);

        String actualExpression =  getField("view", TextView.class).getText().toString();
        ScreenMode actualScreenMode = getField("mode", ScreenMode.class);

        assertEquals(expectedExpression, actualExpression);
        assertEquals(expectedScreenMode, actualScreenMode);
    }

    private <T> T getField(String fieldName, Class<T> clazz) {
        Field field = Helper.getField(screen, fieldName);
        return Helper.getField(field, screen, clazz);
    }

    private <T> void setField(String fieldName, T value) {
        Field field = Helper.getField(screen, fieldName);
        Helper.setField(field, screen, value);
    }

}
