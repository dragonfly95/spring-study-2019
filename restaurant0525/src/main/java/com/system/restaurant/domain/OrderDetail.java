package com.system.restaurant.domain;

import lombok.Data;

@Data
public class OrderDetail {
     
    private int order_detail_id;
    private int order_id;
    private int menu_id;
    private String message;

    public OrderDetail() {}

    public OrderDetail(int order_detail_id, int order_id, int menu_id, String message) {
        this.order_detail_id = order_detail_id;
        this.order_id = order_id;
        this.menu_id = menu_id;
        this.message = message;
    }
}
