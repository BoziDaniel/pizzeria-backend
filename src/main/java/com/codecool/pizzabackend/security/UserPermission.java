package com.codecool.pizzabackend.security;

import com.google.common.collect.Sets;

public enum UserPermission {
    CUSTOMER_READ("customer:read"),
    COOK_READ("cook:read"),
    MANAGER_READ("manager:read"),
    DELIVERY_GUY_READ("deliveryguy:read"),
    CUSTOMER_WRITE("customer:write"),
    COOK_WRITE("cook:write"),
    MANAGER_WRITE("manager:write"),
    DELIVERY_GUY_WRITE("deliveryguy:write");


    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
