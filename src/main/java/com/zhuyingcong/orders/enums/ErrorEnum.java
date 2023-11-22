package com.zhuyingcong.orders.enums;

public enum ErrorEnum {

    INVALID_INPUT(1, "Invalid Input"),
    ;

    private int code;
    private String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
