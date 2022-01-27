package com.fancysoft.calculator.model;

import com.fancysoft.calculator.enums.ScreenMode;

import java.io.Serializable;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Used to save screen data when changing app orientation
 */
@Generated
@Getter
@RequiredArgsConstructor
public class ScreenState implements Serializable {

    private final String expression;
    private final ScreenMode mode;
}
