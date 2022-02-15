package com.fancysoft.calculator.rpn.operation.enums;
import com.fancysoft.calculator.rpn.operation.UnaryOperation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents unary operations
 */
@Getter
@RequiredArgsConstructor
public enum Unary implements UnaryOperation {

    PERCENT("%") {

        @Override
        public double calculate(double a) {
            return a / 100;
        }
    },
    SIN("sin") {

        @Override
        public double calculate(double a) {
            return Math.sin(Math.toRadians(a));
        }
    },
    COS("cos") {

        @Override
        public double calculate(double a) {
            return Math.cos(Math.toRadians(a));
        }
    },
    TAN("tan") {

        @Override
        public double calculate(double a) {
            return Math.tan(Math.toRadians(a));
        }
    },
    COT("cot") {

        @Override
        public double calculate(double a) {
            return 1.0 / TAN.calculate(a);
        }
    };

    private final String value;
}

