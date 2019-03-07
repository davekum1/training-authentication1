package com.kollaboralabs.auth.util;

import org.springframework.util.StringUtils;

import com.kollaboralabs.auth.config.Assert;
import com.kollaboralabs.auth.config.ErrorMessageWrapper;
import com.kollaboralabs.auth.domain.UserRequestData;
import com.kollaboralabs.auth.entity.User;

public class UserValidator {
    private User user;
    private UserRequestData userRequestData;
    private ErrorMessageWrapper validationErrors;

    public UserValidator(User user, UserRequestData userRequestData) {
        Assert.notNull(userRequestData, "userRequestData");
        this.user = user;
        this.userRequestData = userRequestData;
        this.validationErrors = new ErrorMessageWrapper("error.validation");
    }

    public void validate() {
        boolean isUpdate = (this.user != null) ? true : false;
        this.validateNotEmpty("firstName", this.userRequestData.getFirstName(), isUpdate);
        this.validateNotEmpty("lastName", this.userRequestData.getLastName(), isUpdate);
        this.validateNotEmpty("email", this.userRequestData.getEmail(), isUpdate);
        this.validateNotEmpty("login", this.userRequestData.getLogin(), isUpdate);       
    }

    private void validateNotEmpty(String fieldName, String value, boolean isUpdate) {
        if (isUpdate && value == null) {
            return;
        }

        if (StringUtils.isEmpty(value)) {
            this.validationErrors.addErrorMessage(fieldName, "notNullOrEmpty", fieldName, User.class.getSimpleName());
        }
    }

    private void validateMinLength(String fieldName, String value, int minLength, boolean isUpdate) {
        if (isUpdate && value == null) {
            return;
        }

        if (StringUtils.isEmpty(value)) {
            this.validationErrors.addErrorMessage(fieldName, "notNullOrEmpty", fieldName, User.class.getSimpleName());
        } else if (value.length() < minLength) {
            this.validationErrors.addErrorMessage(fieldName, "minLength", minLength, User.class.getSimpleName(), fieldName);
        }
    }

    /**
     * Get error messages
     * @return errorMessageWrapper list of error messages
     */
    public ErrorMessageWrapper getValidationErrors() {
        return validationErrors;
    }
}