package com.kollaboralabs.auth.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorMessageWrapper {
    private Map<String, List<String>> errorMessages = new HashMap<String, List<String>>();
    private String fieldKey;

    /**
     * Field key definition in the message properties
     * For example:
     * In message.properties, there is definition as below
     * password.mustmatch=Passwords do not match.
     *
     * "password" is the fieldKey that should be passed in the constructor to make this holder that contain all error messages
     * for password related properties
     *
     * @param fieldKey key in message properties
     */
    public ErrorMessageWrapper(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    /**
     * Retrieve all error messages
     * @return ListString list of error messages
     */
    public Map<String, List<String>> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Retrieve all error messages
     * @return ListString list of error messages
     */
    public List<String> getErrorMessagesAsList() {
        List<String> list = new ArrayList<String>();
        for (List<String> values: errorMessages.values()) {
            for (String value: values) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * Convenient method to set all error messages, although this probably rarely being used
     * @param errorMessages list of error messages
     */
    public void setErrorMessages(Map<String, List<String>> errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * Add error message to list one at time
     * @param columnKey The 'key' to use in the Map of error messages returned to the user.
     *                  This will be shown as the key in the Map of messages.
     * @param fieldString The key description that identifies the error key in messages.properties.
     * @param fields The parameters from to be used in constructing the message.
     */
    public void addErrorMessage(String columnKey, String fieldString, Object... fields) {
        String message = MessageResource.getInstance().resolveMessage(this.fieldKey + "." + fieldString, fields);
        List<String> currentMessages = this.errorMessages.get(columnKey) != null ? this.errorMessages.get(columnKey) : new ArrayList<String>();
        currentMessages.add(message);
        this.errorMessages.put(columnKey, currentMessages);
    }

}
