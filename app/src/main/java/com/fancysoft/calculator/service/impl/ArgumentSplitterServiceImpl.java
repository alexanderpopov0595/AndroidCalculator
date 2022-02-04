package com.fancysoft.calculator.service.impl;

import com.fancysoft.calculator.service.ArgumentSplitterService;
import com.fancysoft.calculator.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class ArgumentSplitterServiceImpl implements ArgumentSplitterService {

    /**
     * List of all operations
     */
    private final String[] operations;

    public ArgumentSplitterServiceImpl() {
        operations = new String[]{"+", "-", "x", "÷", "%", "(", ")", "^", "√", "sin", "cos", "tan", "cot"};
    }

    @Override
    public List<String> splitToArguments(String expression) {
        expression = addSpace(expression);
        String[] splitted = split(expression);

        return splitToArguments(splitted);
    }

    private String addSpace(String expression) {
        for (String operation : operations) {
            if (expression.contains(operation)) {
                expression = expression.replace(operation, Constants.SPACE + operation + Constants.SPACE);
            }
        }
        return expression;
    }

    private String[] split(String expression) {
        return expression.split(Constants.SPACE);
    }

    private List<String> splitToArguments(String[] splitted) {
        List<String> arguments = new ArrayList<>();

        for (String argument : splitted) {
            if (!argument.isEmpty()) {
                arguments.add(argument);
            }
        }

        return arguments;
    }
}
