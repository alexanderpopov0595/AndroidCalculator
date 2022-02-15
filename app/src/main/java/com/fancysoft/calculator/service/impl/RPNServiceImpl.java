package com.fancysoft.calculator.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fancysoft.calculator.rpn.ArgumentType;
import com.fancysoft.calculator.rpn.operation.BinaryOperation;
import com.fancysoft.calculator.rpn.operation.UnaryOperation;
import com.fancysoft.calculator.service.ArgumentService;
import com.fancysoft.calculator.service.ArgumentSplitterService;
import com.fancysoft.calculator.service.RPNService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.RequiredArgsConstructor;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
public class RPNServiceImpl implements RPNService {

    private final ArgumentSplitterService splitterService;
    private final ArgumentService argService;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public List<String> convertToRPN(String expression) {
        List<String> args = splitterService.splitToArguments(expression);

        List<String> result = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String arg : args) {
            ArgumentType argumentType = argService.getArgumentType(arg);

            if (argumentType == ArgumentType.DIGIT) {
                result.add(arg);
            } else if (argumentType == ArgumentType.OPEN_BRACKET) {
                stack.push(arg);
            } else if (argumentType == ArgumentType.ALGEBRAIC || argumentType == ArgumentType.ARITHMETIC || argumentType == ArgumentType.EXPONENT || argumentType == ArgumentType.UNARY) {
                while (!stack.isEmpty()) {
                    String stackArg = stack.peek();
                    ArgumentType stackArgumentType = argService.getArgumentType(stackArg);
                    if (argumentType.hasHigherPriority(stackArgumentType)) {
                        break;
                    }
                    result.add(stack.pop());
                }
                stack.push(arg);
            } else if (argumentType == ArgumentType.CLOSE_BRACKET) {
                while (argService.getArgumentType(stack.peek()) != ArgumentType.OPEN_BRACKET) {
                    result.add(stack.pop());
                }
                stack.pop();
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public double resolveRPN(List<String> rpn) {
        Stack<Double> stack = new Stack<>();

        for (String arg : rpn) {
            ArgumentType argumentType = argService.getArgumentType(arg);

            if (argumentType == ArgumentType.DIGIT) {
                stack.push(Double.parseDouble(arg));
            } else if (argumentType == ArgumentType.ALGEBRAIC ||
                    argumentType == ArgumentType.ARITHMETIC ||
                    argumentType == ArgumentType.EXPONENT) {
                double b = stack.pop(), a = stack.pop();

                BinaryOperation operation = (BinaryOperation) argService.getOperation(argumentType, arg);

                double c = operation.calculate(a, b);

                stack.push(c);
            } else if (argumentType == ArgumentType.UNARY) {
                double a = stack.pop();

                UnaryOperation operation = (UnaryOperation) argService.getOperation(argumentType, arg);
                double c = operation.calculate(a);

                stack.push(c);
            }
        }
        return stack.pop();
    }
}
