package com.fancysoft.calculator.rpn.operation.enums;

import com.fancysoft.calculator.rpn.operation.Operation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents brackets in math expression
 */
@Getter
@RequiredArgsConstructor
public enum Bracket implements Operation {

    OPEN("("),
    CLOSE(")");

    private final String value;
}
