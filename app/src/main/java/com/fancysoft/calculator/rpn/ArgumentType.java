package com.fancysoft.calculator.rpn;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fancysoft.calculator.rpn.operation.Operation;
import com.fancysoft.calculator.rpn.operation.enums.Algebraic;
import com.fancysoft.calculator.rpn.operation.enums.Arithmetic;
import com.fancysoft.calculator.rpn.operation.enums.Bracket;
import com.fancysoft.calculator.rpn.operation.enums.Exponent;
import com.fancysoft.calculator.rpn.operation.enums.Unary;
import com.fancysoft.calculator.utils.Helper;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents groups of math operations and it's symbols
 */
@RequiresApi(api = Build.VERSION_CODES.R)
@Getter
@RequiredArgsConstructor
public enum ArgumentType {

    CLOSE_BRACKET(Set.of(Bracket.CLOSE)),
    DIGIT(Set.of()) {
        @Override
        public boolean isOperation(String operation) {
            return Helper.isNumeric(operation);
        }
    },
    OPEN_BRACKET(Set.of(Bracket.OPEN)),
    ARITHMETIC(Set.of(Arithmetic.values())),
    ALGEBRAIC(Set.of(Algebraic.values())),
    EXPONENT(Set.of(Exponent.values())),
    UNARY(Set.of(Unary.values()));

    /**
     * Set of operations related to concrete operation
     */
    private final Set<Operation> operations;

    /**
     * Checks if operation related to the group
     *
     * @param value - operation to check
     * @return true if related
     */
    public boolean isOperation(String value) {
        for (Operation operation : operations) {
            if (operation.isOperation(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasHigherPriority(ArgumentType type) {
        return this.ordinal() > type.ordinal();
    }
}
