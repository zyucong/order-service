package com.zhuyingcong.orders.enums;

public enum OrderStatus {

    UNKNOWN(0, "UNKNOWN"),
    UNASSIGNED(1, "UNASSIGNED"),
    TAKEN(2, "TAKEN"),
    ;

    private int status;
    private String description;

    OrderStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }
}
