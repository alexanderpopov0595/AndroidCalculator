package com.fancysoft.calculator.service.impl;

import com.fancysoft.calculator.exception.model.AppException;
import com.fancysoft.calculator.service.CalculatorService;

/**
 * {@inheritDoc}
 */
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public double sum(double a, double b) {
        return a + b;
    }

    @Override
    public double subtract(double a, double b) {
        return a - b;
    }

    @Override
    public double multiply(double a, double b) {
        return a * b;
    }

    @Override
    public double divide(double a, double b) {
        double n = a / b;
        if (Double.isNaN(n) || Double.isInfinite(n)) {
            throw new AppException("Zero division");
        }
        return n;
    }

    @Override
    public double power(double a, double b) {
        return Math.pow(a, b);
    }

    @Override
    public double root(double a, double b) {
        double power = power(a, 1.0/b);
        return (double) Math.round(power);
    }

    @Override
    public double percent(double a) {
        return divide(a, 100);
    }

    @Override
    public double sin(double a) {
        return Math.sin(toRadians(a));
    }

    @Override
    public double cos(double a) {
        return Math.cos(toRadians(a));
    }

    @Override
    public double tan(double a) {
        return Math.tan(toRadians(a));
    }

    @Override
    public double cot(double a) {
        return 1.0 / tan(a);
    }

    private double toRadians(double a) {
        return Math.toRadians(a);
    }
}
