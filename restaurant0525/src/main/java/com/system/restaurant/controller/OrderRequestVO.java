package com.system.restaurant.controller;

import com.system.restaurant.domain.Order;
import com.system.restaurant.domain.OrderDetail;

import java.util.List;

//@Data
public class OrderRequestVO extends Order  {

    private List<OrderDetail> order_detail;

    public List<OrderDetail> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrderDetail> order_detail) {
        this.order_detail = order_detail;
    }

    @Override
    public String toString() {
        return "OrderRequestVO{" +
                "order_detail=" + order_detail +
                '}';
    }
}
