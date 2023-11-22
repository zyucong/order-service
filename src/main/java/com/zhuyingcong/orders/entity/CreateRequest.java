package com.zhuyingcong.orders.entity;

import com.sun.istack.internal.NotNull;

import java.util.List;

public class CreateRequest {

    @NotNull
    private List<String> origin;

    @NotNull
    private List<String> destination;

    public List<String> getOrigin() {
        return origin;
    }

    public void setOrigin(List<String> origin) {
        this.origin = origin;
    }

    public List<String> getDestination() {
        return destination;
    }

    public void setDestination(List<String> destination) {
        this.destination = destination;
    }
}
