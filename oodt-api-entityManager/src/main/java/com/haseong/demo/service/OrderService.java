package com.haseong.demo.service;

import com.haseong.demo.entity2.Order;
import com.haseong.demo.entity2.OrderProduct;
import com.haseong.demo.entity2.Shipping;
import com.haseong.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;


@Service
public class OrderService {

  @Resource
  private OrderRepository orderRepository;

  @Autowired
  private EntityManager em;


  @Transactional
  public List<Order> findAll() {
//    makeTestData();
    return orderRepository.findAll();
  }

  private void makeTestData() {

    int orderProductId = 0;
    for (int i = 0; i < 10; i++) {
      Shipping shipping = new Shipping();
      shipping.setId(i);

      Order order = new Order();
      order.setId(i);
      order.setShipping(shipping);

      for (int j = 0; j < 5; j++) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(orderProductId);
        orderProduct.setOrder(order);
        orderProductId++;
      }

      em.persist(order);
    }

    em.flush();
    em.clear();
  }

}
