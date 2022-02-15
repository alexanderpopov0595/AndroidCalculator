package com.fancysoft.calculator.rpn.operation.enums;

import com.fancysoft.calculator.rpn.operation.BinaryOperation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents exponent operations
 */
@Getter
@RequiredArgsConstructor
public enum Exponent implements BinaryOperation {

    POWER("^") {

        @Override
        public double calculate(double a, double b) {
            return Math.pow(a, b);
        }
    },
    ROOT("√") {

        @Override
        public double calculate(double a, double b) {
            double power = POWER.calculate(b, 1.0 / a);
            return (double) Math.round(power);
        }
    };

    private final String value;
}
