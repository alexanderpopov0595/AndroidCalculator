package com.fancysoft.calculator.service.impl;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fancysoft.calculator.R;
import com.fancysoft.calculator.service.CommandTransformer;

import java.util.Map;

import lombok.RequiredArgsConstructor;

/**
 * {@inheritDoc}
 */

@RequiredArgsConstructor
public class CommandTransformerImpl implements CommandTransformer {

    private final Context context;
    private Map<String, String> map;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void init() {
        map = Map.of(getResource(R.string.square_pow), "^2",
                getResource(R.string.pow), "^",
                getResource(R.string.root), "√");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String transform(String command) {
        String value = map.get(command);
        return value != null ? value : command;
    }

    private String getResource(int resource) {
        return context.getString(resource);
    }
}
