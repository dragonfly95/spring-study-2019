package com.haseong.demo.repository;

import com.haseong.demo.entity2.*;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  @Transactional
  public Page<Order> getCartProducts(Pageable pageable) {

    final QOrderProduct orderProduct = QOrderProduct.orderProduct;
    final QOrder order = QOrder.order;
    final QShipping shipping = QShipping.shipping;

    JPAQuery query = new JPAQuery(entityManager);
    JPAQuery fetch = query.from(order).
        distinct().leftJoin(order.orderProducts, orderProduct)
        .fetch()
        .leftJoin(order.shipping, shipping)
        .fetch();
    List<Order> list = fetch.list(order);
    return new PageImpl<Order>(list, pageable, query.count());
  }
}
