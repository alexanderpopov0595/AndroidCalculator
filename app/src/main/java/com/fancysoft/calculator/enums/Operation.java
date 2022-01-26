package com.fancysoft.calculator.enums;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fancysoft.calculator.utils.Constants;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents groups of math operations and it's symbols
 */
@RequiresApi(api = Build.VERSION_CODES.R)
@Getter
@RequiredArgsConstructor
public enum Operation {

    SPACE(Set.of(Constants.SPACE)),
    CLOSE_BRACKET(Set.of(')')),
    DIGIT(Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.')),
    OPEN_BRACKET(Set.of('(')),
    ARITHMETIC(Set.of('+', '-')),
    ALGEBRAIC(Set.of('x', '/')),
    EXPONENT(Set.of('^', '√')),
    UNARY(Set.of('%'));

    /**
     * Set of symbols related to concrete operation
     */
    private final Set<Character> symbols;
}
