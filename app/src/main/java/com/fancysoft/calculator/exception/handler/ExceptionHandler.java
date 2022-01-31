package com.fancysoft.calculator.exception.handler;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.fancysoft.calculator.exception.model.AppException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExceptionHandler {

    private final Context context;

    public void handleException(Throwable e) {
        String error = e instanceof AppException ? e.getMessage() : "Invalid expression";

        Toast toast = Toast.makeText(context, error, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 160);
        toast.show();
    }
}
