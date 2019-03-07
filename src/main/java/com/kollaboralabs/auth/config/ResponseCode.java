package com.kollaboralabs.auth.config;

public class ResponseCode {
    @lombok.Getter
    private int status;
    private String key;
    private Object[] fields = new Object[0];

    public ResponseCode(int status, String key, Object... fields) {
        this.status = status;
        this.key = "responseCode." + key;
        this.fields = fields;
    }

    public String getMessage() {
        return MessageResource.getInstance().resolveMessage(this.key, this.fields);
    }
}