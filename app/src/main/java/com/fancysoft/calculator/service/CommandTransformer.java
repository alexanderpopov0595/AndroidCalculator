package com.fancysoft.calculator.service;

/**
 * Converter between button's label to expression's command
 */
public interface CommandTransformer {

    /**
     * Converts label of pressed button to expression's format
     * @param command - button's command
     * @return expression command
     */
    String transform(String command);

    /**
     * Loads properties into memory
     */
    void init();
}
