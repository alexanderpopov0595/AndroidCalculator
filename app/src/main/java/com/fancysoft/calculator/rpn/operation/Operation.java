package com.fancysoft.calculator.rpn.operation;

/**
 * Common interface for math operation
 */
public interface Operation {

    /**
     * Allows to get operation label
     * @return label
     */
    String getValue();

    /**
     * Checks if provided value relates to current operation
     * @param value - value to check
     * @return true if related
     */
    default boolean isOperation(String value) {
        return getValue().equals(value);
    }
}
