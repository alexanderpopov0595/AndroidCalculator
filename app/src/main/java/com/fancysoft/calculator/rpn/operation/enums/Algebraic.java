package com.fancysoft.calculator.rpn.operation.enums;

import com.fancysoft.calculator.exception.model.AppException;
import com.fancysoft.calculator.rpn.operation.BinaryOperation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents algebraic operations
 */
@Getter
@RequiredArgsConstructor
public enum Algebraic implements BinaryOperation {

    MULTIPLY("×") {

        @Override
        public double calculate(double a, double b) {
            return a * b;
        }
    },
    DIVIDE("÷") {

        @Override
        public double calculate(double a, double b) {
            double c = a / b;
            if (Double.isNaN(c) || Double.isInfinite(c)) {
                throw new AppException("Zero division");
            }
            return c;
        }
    };

    private final String value;
}