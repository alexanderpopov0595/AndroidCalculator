package com.fancysoft.calculator.service;

import java.util.List;

/**
 * Provides method to split math expression to single arguments
 */
public interface ArgumentSplitterService {

    /**
     * Splits expression into args
     * @param expression - entered math expression
     * @return args array
     */
    List<String> splitToArguments(String expression);
}
