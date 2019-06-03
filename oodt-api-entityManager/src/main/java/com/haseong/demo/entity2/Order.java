package com.haseong.demo.entity2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "NPLUS_ORDER")
public class Order {

  @Id
  @JsonProperty
  private int id;


  @OneToOne(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
  @BatchSize(size = 10)
  @JsonProperty
  private Shipping shipping;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
  @Fetch(FetchMode.SUBSELECT)
  @JsonProperty
  private Set<OrderProduct> orderProducts = new LinkedHashSet<>();

  
//    final QOrderProduct orderProduct = QOrderProduct.orderProduct;
//    final QOrder order = QOrder.order;
//    final QShipping shipping = QShipping.shipping;
//
//    JPAQuery query = new JPAQuery(entityManager);
//    JPAQuery fetch = query.from(order).
//        distinct().leftJoin(order.orderProducts, orderProduct)
//        .fetch()
//        .leftJoin(order.shipping, shipping)
//        .fetch();
//    List<Order> list = fetch.list(order);
  
  
  public void setShipping(Shipping shipping) {
    this.shipping = shipping;
    shipping.setOrder(this);
  }

  public void addOrderProducts(OrderProduct orderProduct) {
    orderProducts.add(orderProduct);
  }

}
