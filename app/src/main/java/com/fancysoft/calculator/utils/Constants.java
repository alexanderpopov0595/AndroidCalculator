package com.fancysoft.calculator.utils;

/**
 * Contains application constants
 */
public class Constants {

    /**
     * Value to display on screen when no expression is entered
     */
    public static final String SCREEN_DEFAULT_VALUE = "0";

    /**
     * Delta to compare two double values
     */
    public static final double DELTA = 1e-15;

    /**
     * Space symbol for RPN operations
     */
    public static final String SPACE = " ";

    /**
     * Used to check if argument is numeric
     */
    public static final String NUMERIC_PATTERN = "-?\\d+(\\.\\d+)?";

    /**
     * Used to split math expression into arguments
     */

    //public static final String ARGUMENT_PATTERN = "(?<=\\b(?:sin|cos|tan|cot))|(?<=[-+^×%√÷()])|(?=\\b(?:sin|cos|tan|cot))|(?=[-+^×%√÷()])";
    public static final String ARGUMENT_PATTERN = "(?<=[-+^×%√÷()]|sin|cos|tan|cot)|(?=[-+^×%√÷()]|sin|cos|tan|cot)";

    /**
     * Key to save screen state in bundle
     */
    public static final String SCREEN_STATE = "SCREEN_STATE";
}
