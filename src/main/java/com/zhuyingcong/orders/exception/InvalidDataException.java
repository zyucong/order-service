package com.zhuyingcong.orders.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
