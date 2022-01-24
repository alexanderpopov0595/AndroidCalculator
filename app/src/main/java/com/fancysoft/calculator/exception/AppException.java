package com.fancysoft.calculator.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AppException extends RuntimeException{

    private final String message;
}
