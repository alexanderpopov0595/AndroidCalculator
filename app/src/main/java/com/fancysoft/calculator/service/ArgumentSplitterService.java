package com.fancysoft.calculator.service;

import java.util.List;

/**
 * Provides method to split math expression to single arguments
 */
public interface ArgumentSplitterService {

    List<String> splitToArguments(String expression);
}
