package com.fims.common.domain;

/**
 * Created by Administrator on 2018/8/14.
 */
public class Suggest {
    private String message;

    private Object value;

    public Suggest(String message, Object value) {
        this.message = message;
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
