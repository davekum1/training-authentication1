package com.kollaboralabs.auth.exception;

public class EntityAlreadyInUseException extends RuntimeException {
    private Class<?> entityClass;
    private String columnName;
    private Object columnValue;

    public EntityAlreadyInUseException(Class<?> entityClass, String columnName, Object columnValue) {
        this.entityClass = entityClass;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public String getEntityClassName() {
        return entityClass.getSimpleName();
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnValue() {
        return columnValue.toString();
    }
}