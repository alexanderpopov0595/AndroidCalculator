package com.fancysoft.calculator.model;

import android.widget.TextView;

import com.fancysoft.calculator.enums.ScreenMode;
import com.fancysoft.calculator.utils.Constants;

/**
 * Handles all operations with the calculator's screen view
 */
public class Screen {

    /**
     * UI reference object
     */
    private final TextView view;
    /**
     * Input mode
     */
    private ScreenMode mode;

    public Screen(TextView view) {
        this.view = view;
        view.setText(Constants.SCREEN_DEFAULT_VALUE);
        mode = ScreenMode.START;
    }

    /**
     * Displays user's command on screen
     * If no expression was entered before - input starts from the beginning
     * If some expression was already entered - new command appends to the end of expression
     * @param command - entered command
     */
    public void display(String command) {
        if (mode == ScreenMode.START) {
            mode = ScreenMode.CONTINUE;
            view.setText(command);
        }
        else {
            view.append(command);
        }
    }

    /**
     * Displays result on the screen
     * @param result - result of executed expression
     */
    public void display(double result) {
        if (hasDecimal(result)) {
            view.setText(String.valueOf(result));
        }
        else {
            view.setText(String.valueOf((int) result));
        }
        mode = ScreenMode.START;
    }

    /**
     * Checks if number has decimal part
     * @param n - double value
     * @return true if value has decimal part
     */
    private boolean hasDecimal(double n) {
        return n % 1 != 0;
    }

    /**
     * Removes expression from the screen
     */
    public void clear() {
        mode = ScreenMode.START;
        view.setText(Constants.SCREEN_DEFAULT_VALUE);
    }

    /**
     * Removes last character of expression from the screen
     */
    public void backspace() {
        if (hasExpression() && !isSingleCommand()) {
            deleteLastCommand();
        }
        else {
            clear();
        }
    }

    /**
     * Checks if some expression was entered
     * @return false if expression == default screen value
     */
    private boolean hasExpression() {
        return !view.getText().equals(Constants.SCREEN_DEFAULT_VALUE);
    }

    /**
     * Checks if expression has one single command
     * @return true if expression has one command
     */
    private boolean isSingleCommand() {
        return view.getText().length() == 1;
    }

    /**
     * Removes last char from expression
     */
    private void deleteLastCommand() {
        view.setText(view.getText().subSequence(0, view.getText().length() - 1));
    }

    /**
     * Retrieves expression from the view
     * @return view's text
     */
    public String getExpression() {
        return view.getText().toString();
    }

    /**
     * Retrieves screen data
     * @return screen state
     */
    public ScreenState getScreenState() {
        return new ScreenState(view.getText().toString(), mode);
    }

    /**
     * Sets screen data
     * @param state - transfer object containing entered expression and screen mode
     */
    public void setScreenState(ScreenState state) {
        view.setText(state.getExpression());
        mode = state.getMode();
    }

}
