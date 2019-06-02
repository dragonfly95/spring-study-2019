package com.haseong.demo.repository;

import com.haseong.demo.entity2.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
  Page<Order> getCartProducts(Pageable pageable);

}
