package com.fancysoft.calculator.service.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fancysoft.calculator.service.CommandTransformer;

import java.util.Map;

/**
 * {@inheritDoc}
 */
public class CommandTransformerImpl implements CommandTransformer {

    private final Map<String, String> map;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public CommandTransformerImpl() {
        map = Map.of("x2", " ^ 2 ",
                    "xy", " ^ ",
                    "x√y", " √ ");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String transform(String command) {
        String value = map.get(command);
        return value != null ? value : command;
    }
}
