package com.fancysoft.calculator.service;

import com.fancysoft.calculator.rpn.ArgumentType;
import com.fancysoft.calculator.rpn.operation.Operation;

/**
 * Provides methods to work with RPN operations
 */
public interface ArgumentService {

    /**
     * Defines an operation type by value
     * @param value - symbol possibly related to the one of operations
     * @return argument type if value is found
     */
    ArgumentType getArgumentType(String value);

    /**
     * Allows to get operation by type and value
     * @param type - type of operation
     * @param value - operation label
     * @return operation
     */
    Operation getOperation(ArgumentType type, String value);
}
