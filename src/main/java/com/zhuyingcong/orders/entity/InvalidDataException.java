package com.zhuyingcong.orders.entity;

public class InvalidDataException extends Exception {

    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
