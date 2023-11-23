package com.zhuyingcong.orders.enums;

public enum ErrorEnum {

    INVALID_INPUT(1, "Invalid input"),
    CHANGE_STATUS_FAIL(2, "Unable to change order status")
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
