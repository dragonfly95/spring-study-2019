package com.haseong.demo.controller;




import com.haseong.demo.entity2.Order;
import com.haseong.demo.repository.OrderProductRepository;
import com.haseong.demo.repository.OrderRepository;
import com.haseong.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private OrderProductRepository orderProductRepository;

  @Autowired
  private EntityManager em;

  // no entitymanager with actual transaction available for current thread persist
  @RequestMapping(value = "/orders")
  public Page<Order> findAll(Pageable pageable) {

    return orderRepository.getCartProducts(pageable);
//    return orderService.findAll();
  }


}
