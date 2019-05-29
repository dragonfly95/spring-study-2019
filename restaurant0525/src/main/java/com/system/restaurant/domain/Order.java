package com.system.restaurant.domain;


import lombok.Data;

@Data
public class Order {

    private int order_id;
    private String user_name;
    private String user_id;
    private float total_cost;
    private String cooking_status;

    public Order() {}

    public Order(int order_id, String user_name, String user_id, float total_cost, String cooking_status) {
        this.order_id = order_id;
        this.user_name = user_name;
        this.user_id = user_id;
        this.total_cost = total_cost;
        this.cooking_status = cooking_status;
    }
}

