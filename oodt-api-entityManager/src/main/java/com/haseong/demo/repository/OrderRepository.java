package com.haseong.demo.repository;

import com.haseong.demo.entity2.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, OrderRepositoryCustom {

  @Override
  @EntityGraph(attributePaths = {"shipping"})
  List<Order> findAll();

  @Override
  @EntityGraph(attributePaths = {"shipping"})
  Page<Order> findAll(Pageable pageable);
}

