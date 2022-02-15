package com.fancysoft.calculator.rpn.operation.enums;

import com.fancysoft.calculator.rpn.operation.BinaryOperation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents arithmetic operations
 */
@Getter
@RequiredArgsConstructor
public enum Arithmetic implements BinaryOperation {

    SUM("+") {

        @Override
        public double calculate(double a, double b) {
            return a + b;
        }
    },
    SUBTRACT("-") {

        @Override
        public double calculate(double a, double b) {
            return a - b;
        }
    };

    private final String value;
}
