package com.zhuyingcong.orders.enums;

public enum ResultStatus {

    UNKNOWN(0, "UNKNOWN"),
    SUCCESS(1, "SUCCESS"),
    FAIL(2, "FAIL"),
    ;

    private int status;
    private String description;

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    ResultStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }
}
