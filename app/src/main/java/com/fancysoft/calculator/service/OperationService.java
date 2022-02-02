package com.fancysoft.calculator.service;

import com.fancysoft.calculator.enums.Operation;

/**
 * Provides methods to work with RPN operations
 */
public interface OperationService {

    /**
     * Check if first operation has higher priority than second
     * @param op1 - first operation
     * @param op2 - second operation
     * @return true if first has higher priority
     */
    boolean hasHigherPriority(Operation op1, Operation op2);


    /**
     * Defines an operation type by symbol
     * @param operation - symbol possibly related to the one of operations
     * @return operation type if symbol is found
     */
    Operation getOperation(String operation);
}
