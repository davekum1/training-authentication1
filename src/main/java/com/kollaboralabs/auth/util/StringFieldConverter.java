package com.kollaboralabs.auth.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.kollaboralabs.auth.config.Assert;


public abstract class StringFieldConverter {
    public static void trimStringFields(Object object, List<String> fieldNames) {
        Assert.notNull(object, "objectToTrim");
        Assert.notEmpty(fieldNames, "fieldNames");

        List<Field> fields = Arrays.asList(FieldUtils.getAllFields(object.getClass()));
        for (Field field : fields) {
            if (fieldNames.contains(field.getName())) {
                trimField(object, field);
            }
        }
    }

    public static void trimStringFields(Object object) {
        Assert.notNull(object, "objectToTrim");

        Field[] fields = FieldUtils.getAllFields(object.getClass());
        for (Field field : fields) {
            trimField(object, field);
        }
    }

    public static boolean isAlphaNumeric(String value) {
        if (NumberUtils.isCreatable(value)) {
            return false;
        }
        if (BooleanUtils.toBooleanObject(value) != null) {
            return false;
        }
        return true;
    }

    private static void trimField(Object object, Field field) {
        if (field.getType().equals(String.class)) {
            try {
                field.setAccessible(true);
                String value = (String) field.get(object);
                if (value != null) {
                    field.set(object, value.trim());
                }
            } catch (IllegalAccessException e) {
                // Throw our standard exception for bad parameters
                throw new IllegalArgumentException(e);
            }
        }
    }

}