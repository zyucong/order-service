package com.zhuyingcong.orders.enums;

public enum OrderStatus {

    UNKNOWN(0, "UNKNOWN"),
    UNASSIGNED(1, "UNASSIGNED"),
    TAKEN(2, "TAKEN"),
    ;

    private int status;
    private String description;

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    OrderStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public static String findDescription(int status) {
        for (OrderStatus enums : values()) {
            if (enums.getStatus() == status) {
                return enums.getDescription();
            }
        }
        return null;
    }
}
