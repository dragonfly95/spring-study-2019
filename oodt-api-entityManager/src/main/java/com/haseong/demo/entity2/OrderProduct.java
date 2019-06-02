package com.haseong.demo.entity2;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@lombok.ToString(exclude = "order")
@Entity
public class OrderProduct {

  @Id
  @JsonProperty
  private int id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "order_id", nullable = false)
  @BatchSize(size = 10)
  @JsonIgnore
  private Order order;

  public void setOrder(Order order) {
    this.order = order;
    order.addOrderProducts(this);
  }
}
