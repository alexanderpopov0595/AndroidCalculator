package com.fancysoft.calculator.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.util.Arrays;

import lombok.experimental.UtilityClass;

/**
 * Provides help methods
 */
@UtilityClass
public class Helper {

    /**
     * Retrieves field by name from reference object using reflection
     * @param refObj - object with required field
     * @param fieldName - name of required field
     * @return required field
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Field getField(Object refObj, String fieldName) {
        Class<?> clazz = refObj.getClass();

        Field[] fields = clazz.getDeclaredFields();

        Field field = Arrays.stream(fields)
                .filter(f -> f.getName().equals(fieldName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't get field through reflection"));

        field.setAccessible(true);

        return field;
    }

    /**
     * Gets a field value
     * @param field - field containing the value
     * @param refObj - object containing field
     * @param clazz - class to cast
     * @param <T> - class type
     * @return - field value
     */
    public static <T> T getField(Field field, Object refObj, Class<T> clazz) {
        try {
            return clazz.cast(field.get(refObj));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Sets value to field
     * @param field - field to set value
     * @param refObj - object containing field
     * @param value - value which should be set
     */
    public static void setField(Field field, Object refObj, Object value) {
        try {
            field.set(refObj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Checks if string value is numeric
     * @param value - string to check
     * @return - true if numeric
     */
    public static boolean isNumeric(String value) {
        return value.matches(Constants.NUMERIC_PATTERN);
    }
}
