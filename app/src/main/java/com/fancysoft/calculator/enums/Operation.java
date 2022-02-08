package com.fancysoft.calculator.enums;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.fancysoft.calculator.utils.Helper;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents groups of math operations and it's symbols
 */
@RequiresApi(api = Build.VERSION_CODES.R)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Operation {

    CLOSE_BRACKET(Set.of(")")),
    DIGIT() {
        @Override
        public boolean isOperation(String operation) {
            return Helper.isNumeric(operation);
        }
    },
    OPEN_BRACKET(Set.of("(")),
    ARITHMETIC(Set.of("+", "-")),
    ALGEBRAIC(Set.of("×", "÷")),
    EXPONENT(Set.of("^", "√")),
    UNARY(Set.of("%", "sin", "cos", "tan", "cot"));

    /**
     * Set of symbols related to concrete operation
     */
    private Set<String> symbols;

    /**
     * Checks if operation related to the group
     * @param operation - operation to check
     * @return true if related
     */
    public boolean isOperation(String operation) {
        return this.symbols.contains(operation);
    }
}
