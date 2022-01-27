package com.fancysoft.calculator.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fancysoft.calculator.enums.Operation;
import com.fancysoft.calculator.exception.model.AppException;
import com.fancysoft.calculator.service.OperationService;

/**
 * {@inheritDoc}
 */
public class OperationServiceImpl implements OperationService {

    @Override
    public boolean hasHigherPriority(Operation op1, Operation op2) {
        return op1.ordinal() > op2.ordinal();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public Operation getOperation(char operation) {
        for (Operation op : Operation.values()) {
            for (char c : op.getSymbols()) {
                if (c == operation) {
                    return op;
                }
            }
        }
        throw new AppException(String.format("RPN error: operation %c is unknown", operation));
    }
}
