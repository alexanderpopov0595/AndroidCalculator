package com.fancysoft.calculator.service;

import java.util.List;

/**
 * Provides methods for operations in Reverse Polish Notation (RPN) form
 */
public interface RPNService {

    /**
     * Converts expression to RPN
     * @param expression - math expression in classic form
     * @return expression in RPN form
     */
    List<String> convertToRPN(String expression);

    /**
     * Parses rpn expression and evaluates answer
     * @param rpn - expression in rpn form
     * @return result
     */
    double resolveRPN(List<String> rpn);
}
