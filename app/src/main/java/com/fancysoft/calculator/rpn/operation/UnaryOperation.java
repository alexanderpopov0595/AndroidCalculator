package com.fancysoft.calculator.rpn.operation;

/**
 * Represents unary math expression
 */
public interface UnaryOperation extends Operation {

    /**
     * Calculates expression with single arg
     * @param a - first operand
     * @return result
     */
    double calculate(double a);
}
