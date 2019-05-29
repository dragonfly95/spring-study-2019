package com.system.restaurant.service;

import com.system.restaurant.domain.Order;
import com.system.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class OrderService {

    @Resource
    OrderRepository orderRepository;

    public ArrayList<Order> orderlist() {
        return orderRepository.orderlist();
    }

    public Order findById(int id) {
        return orderRepository.findById(id);
    }

    public int post(Order orderdata) {
        return orderRepository.post(orderdata);
    }

    public int put(Order orderdata) {
        return orderRepository.put(orderdata);
    }

    public int delete(int order_id) {
        return orderRepository.delete(order_id);
    }
}
