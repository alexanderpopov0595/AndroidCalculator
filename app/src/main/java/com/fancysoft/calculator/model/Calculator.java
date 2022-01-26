package com.fancysoft.calculator.model;

import com.fancysoft.calculator.service.RPNService;
import lombok.RequiredArgsConstructor;

/**
 * Backend for calculator UI
 */
@RequiredArgsConstructor
public class Calculator {

    /**
     * Manages screen's operations
     */
    private final Screen screen;
    /**
     * Converts expressions to RPN form and resolves it
     */
    private final RPNService service;

    /**
     * Passes entered command to the screen
     * @param command - button's command
     */
    public void display(String command) {
        screen.display(command);
    }

    /**
     * Executes entered expression and passes it to the screen
     */
    public void execute() {
        String expression = screen.getExpression();
        String rpn = service.convertToRPN(expression);
        double result = service.resolveRPN(rpn);
        screen.display(result);
    }

    /**
     * Removes expression from the screen
     */
    public void clear() {
        screen.clear();
    }

    /**
     * Deletes last entered character from the screen
     */
    public void backspace() {
        screen.backspace();
    }
}