package com.fancysoft.calculator.service.impl;

import com.fancysoft.calculator.exception.AppException;
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
        return a / 100;
    }
}
