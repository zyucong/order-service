package com.zhuyingcong.orders.entity;

public class BusinessException extends RuntimeException {

    private String errorMsg;

    public BusinessException() {
        super();
    }

    public BusinessException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
