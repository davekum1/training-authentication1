package com.kollaboralabs.auth.exception;

public class EntityNotFoundException extends RuntimeException {
    private Class<?> entityClass;
    @lombok.Getter
    private String columnName;
    @lombok.Getter
    private String columnValue;

    public EntityNotFoundException(Class<?> entityClass, String columnName, String columnValue) {
        this.entityClass = entityClass;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public String getEntityClassName() {
        return entityClass.getSimpleName();
    }
}
