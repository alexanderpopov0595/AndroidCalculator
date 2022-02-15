package com.fancysoft.calculator.rpn.operation;

/**
 * Represents binary math operation
 */
public interface BinaryOperation extends Operation {

    /**
     * Calculates expression with two args
     * @param a - first operand
     * @param b - second operand
     * @return result
     */
    double calculate(double a, double b);
}
