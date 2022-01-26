package com.fancysoft.calculator.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fancysoft.calculator.enums.Operation;
import com.fancysoft.calculator.exception.AppException;
import com.fancysoft.calculator.service.CalculatorService;
import com.fancysoft.calculator.service.OperationService;
import com.fancysoft.calculator.service.RPNService;
import com.fancysoft.calculator.utils.Constants;

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
    public String convertToRPN(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            Operation operation = opService.getOperation(symbol);

            if (operation == Operation.DIGIT) {
                result.append(symbol);
            }
            else if (operation == Operation.OPEN_BRACKET) {
                stack.push(symbol);
            }
            else if (operation == Operation.ALGEBRAIC || operation == Operation.ARITHMETIC || operation == Operation.EXPONENT || operation == Operation.UNARY) {
                result.append(Constants.SPACE);

                while (!stack.isEmpty()) {
                    char stackSymbol = stack.peek();
                    Operation stackOperation = opService.getOperation(stackSymbol);
                    if (opService.hasHigherPriority(operation, stackOperation)) {
                        break;
                    }
                    result.append(stack.pop());
                }
                stack.push(symbol);
            }
            else if (operation == Operation.CLOSE_BRACKET) {
                result.append(Constants.SPACE);

                while (opService.getOperation(stack.peek()) != Operation.OPEN_BRACKET) {
                    result.append(stack.pop());
                }
                stack.pop();
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public double resolveRPN(String rpn) {
        StringBuilder operand = new StringBuilder();
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            char symbol = rpn.charAt(i);
            Operation operation = opService.getOperation(symbol);

            if (operation == Operation.SPACE) {
                continue;
            }
            else if (operation == Operation.DIGIT) {
                while(opService.getOperation(rpn.charAt(i)) != Operation.SPACE &&
                        opService.getOperation(rpn.charAt(i)) == Operation.DIGIT) {
                    operand.append(rpn.charAt(i++));
                    if(i == rpn.length()) {
                        break;
                    }
                }
                stack.push(Double.parseDouble(operand.toString()));
                operand.setLength(0);
            }
            if (opService.getOperation(rpn.charAt(i)) == Operation.ALGEBRAIC ||
                    opService.getOperation(rpn.charAt(i)) == Operation.ARITHMETIC ||
                    opService.getOperation(rpn.charAt(i)) == Operation.EXPONENT) {
                double b = stack.pop(), a = stack.pop();
                double c;
                switch(rpn.charAt(i)) {
                    case '+' : c = calcService.sum(a, b); break;
                    case '-' : c = calcService.subtract(a, b); break;
                    case 'x' : c = calcService.multiply(a, b); break;
                    case '/' : c = calcService.divide(a, b); break;
                    case '^' : c = calcService.power(a, b); break;
                    case '√' : c = calcService.root(b, a); break;
                    default: throw new AppException(String.format("Can't perform operation: unknown command %c", rpn.charAt(i)));
                }
                stack.push(c);
            }
            else if(opService.getOperation(rpn.charAt(i)) == Operation.UNARY) {
                double a = stack.pop();
                double c;
                switch (rpn.charAt(i)) {
                    case '%' : c = calcService.percent(a); break;
                    default: throw new AppException(String.format("Can't perform operation: unknown command %c", rpn.charAt(i)));
                }
                stack.push(c);
            }
        }
        return stack.pop();
    }
}
