package com.fancysoft.calculator.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fancysoft.calculator.enums.Operation;
import com.fancysoft.calculator.exception.model.AppException;
import com.fancysoft.calculator.service.CalculatorService;
import com.fancysoft.calculator.service.OperationService;
import com.fancysoft.calculator.service.RPNService;
import com.fancysoft.calculator.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lombok.RequiredArgsConstructor;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
public class RPNServiceImpl implements RPNService {

    private final OperationService opService;
    private final CalculatorService calcService;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public List<String> convertToRPN(String expression) {
        List<String> args = splitToArguments(expression);

        List<String> result = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String arg : args) {
            Operation operation = opService.getOperation(arg);

            if (operation == Operation.DIGIT) {
                result.add(arg);
            }
            else if (operation == Operation.OPEN_BRACKET) {
                stack.push(arg);
            }
            else if (operation == Operation.ALGEBRAIC || operation == Operation.ARITHMETIC || operation == Operation.EXPONENT || operation == Operation.UNARY) {
                while (!stack.isEmpty()) {
                    String stackArg = stack.peek();
                    Operation stackOperation = opService.getOperation(stackArg);
                    if (opService.hasHigherPriority(operation, stackOperation)) {
                        break;
                    }
                    result.add(stack.pop());
                }
                stack.push(arg);
            }
            else if (operation == Operation.CLOSE_BRACKET) {
                while (opService.getOperation(stack.peek()) != Operation.OPEN_BRACKET) {
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

    /**
     * Splits expression to single arguments chain
     * @param expression - math expression
     * @return array of arguments
     */
    private List<String> splitToArguments(String expression) {
        List<String> args = new ArrayList<>();
        for (String piece : expression.split(Constants.SPACE)) {
            if (!piece.isEmpty()) {
                args.add(piece);
            }
        }
        return args;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public double resolveRPN(List<String> rpn) {
        Stack<Double> stack = new Stack<>();

        for (String arg : rpn) {
            Operation operation = opService.getOperation(arg);

            if (operation == Operation.DIGIT) {
                stack.push(Double.parseDouble(arg));
            }
            else if (opService.getOperation(arg) == Operation.ALGEBRAIC ||
                    opService.getOperation(arg) == Operation.ARITHMETIC ||
                    opService.getOperation(arg) == Operation.EXPONENT) {
                double b = stack.pop(), a = stack.pop();
                double c;
                switch(arg) {
                    case "+" : c = calcService.sum(a, b); break;
                    case "-" : c = calcService.subtract(a, b); break;
                    case "x" : c = calcService.multiply(a, b); break;
                    case "÷" : c = calcService.divide(a, b); break;
                    case "^" : c = calcService.power(a, b); break;
                    case "√" : c = calcService.root(b, a); break;
                    default: throw new AppException(String.format("Can't perform operation: unknown command %s", arg));
                }
                stack.push(c);
            }
            else if(opService.getOperation(arg) == Operation.UNARY) {
                double a = stack.pop();
                double c;
                switch (arg) {
                    case "%" : c = calcService.percent(a); break;
                    case "sin" : c = calcService.sin(a); break;
                    case "cos" : c = calcService.cos(a); break;
                    case "tan" : c = calcService.tan(a); break;
                    case "cot" : c = calcService.cot(a); break;
                    default: throw new AppException(String.format("Can't perform operation: unknown command %s", arg));
                }
                stack.push(c);
            }
        }
        return stack.pop();
    }
}
