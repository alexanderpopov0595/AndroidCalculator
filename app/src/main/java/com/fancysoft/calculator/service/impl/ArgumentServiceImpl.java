package com.fancysoft.calculator.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fancysoft.calculator.rpn.ArgumentType;
import com.fancysoft.calculator.exception.model.AppException;
import com.fancysoft.calculator.rpn.operation.Operation;
import com.fancysoft.calculator.service.ArgumentService;

/**
 * {@inheritDoc}
 */
public class ArgumentServiceImpl implements ArgumentService {

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public ArgumentType getArgumentType(String value) {
        for (ArgumentType op : ArgumentType.values()) {
            if (op.isOperation(value)) {
                return op;
            }
        }
        throw new AppException(String.format("RPN error: argument type %s is unknown", value));
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public Operation getOperation(ArgumentType type, String value) {
        for (Operation operation : type.getOperations()) {
            if (operation.isOperation(value)) {
                return operation;
            }
        }
        throw new AppException(String.format("RPN error: operation %s is unknown", value));
    }
}
