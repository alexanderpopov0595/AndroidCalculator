package com.fancysoft.calculator.service.impl;

import com.fancysoft.calculator.service.ArgumentSplitterService;
import com.fancysoft.calculator.utils.Constants;
import java.util.Arrays;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class ArgumentSplitterServiceImpl implements ArgumentSplitterService {

    @Override
    public List<String> splitToArguments(String expression) {
        return Arrays.asList(expression.split(Constants.ARGUMENT_PATTERN));
    }
}
