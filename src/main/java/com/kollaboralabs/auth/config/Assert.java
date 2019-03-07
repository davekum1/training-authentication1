package com.kollaboralabs.auth.config;

import java.util.Collection;

import com.kollaboralabs.auth.entity.User;
import com.kollaboralabs.auth.exception.EntityAlreadyInUseException;
import com.kollaboralabs.auth.exception.EntityNotFoundException;

public abstract class Assert {
    public static void notNull(Object reference, String parameterName) {
        org.springframework.util.Assert.notNull(
            reference,
            MessageResource.getInstance().resolveMessage("error.notNull", parameterName));
    }

    public static void hasLength(String reference, String parameterName) {
        org.springframework.util.Assert.hasLength(
            reference,
            MessageResource.getInstance().resolveMessage("error.hasLength", parameterName));
    }

    public static void entityNotNull(Object reference, Class<?> clazz, String columnName, String columnValue) {
        if (reference == null) {
            throw new EntityNotFoundException(clazz, columnName, columnValue);
        }
    }

    public static void notAlreadyInUse(User user, Class<?> clazz, String columnName, String columnValue) {
        if (user != null) {
            throw new EntityAlreadyInUseException(clazz, columnName, columnValue);
        }
    }

    public static void notEmpty(Collection<?> collection, String parameterName) {
        org.springframework.util.Assert.notEmpty(
            collection,
            MessageResource.getInstance().resolveMessage("error.hasLength", parameterName));
    }

    public static void isTrue(boolean expression, String message) {
        org.springframework.util.Assert.isTrue(expression, message);
    }
}