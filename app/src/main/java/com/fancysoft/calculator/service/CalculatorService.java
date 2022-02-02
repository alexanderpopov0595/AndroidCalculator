package com.fancysoft.calculator.service;

/**
 * Perform all math operations
 */
public interface CalculatorService {

    /**
     * Perform addition
     * @param a - first operand
     * @param b - second operand
     * @return result
     */
    double sum(double a, double b);

    /**
     * Perform subtraction
     * @param a - first operand
     * @param b - second operand
     * @return result
     */
    double subtract(double a, double b);

    /**
     * Perform multiplication
     * @param a - first operand
     * @param b - second operand
     * @return result
     */
    double multiply(double a, double b);

    /**
     * Perform division
     * @param a - first operand
     * @param b - second operand
     * @return result
     */
    double divide(double a, double b);

    /**
     * Perform exponentiation
     * @param a - first operand
     * @param b - second operand
     * @return result
     */
    double power(double a, double b);

    /**
     * Perform root operation
     * @param a - first operand
     * @param b - second operand
     * @return result
     */
    double root(double a, double b);

    /**
     * Perform percent operation
     * @param a - first operand
     * @return result
     */
    double percent(double a);

    /**
     * Perform sin operation
     * @param a - operand
     * @return result
     */
    double sin(double a);

    /**
     * Perform cos operation
     * @param a - operand
     * @return result
     */
    double cos(double a);

    /**
     * Perform tan operation
     * @param a - operand
     * @return result
     */
    double tan(double a);

    /**
     * Perform cot operation
     * @param a - operand
     * @return result
     */
    double cot(double a);
}
