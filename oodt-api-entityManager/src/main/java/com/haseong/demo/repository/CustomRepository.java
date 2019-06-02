package com.haseong.demo.repository;

import com.haseong.demo.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomRepository extends CrudRepository<Customer, Long> {
  List<Customer> findByLastName(String lastName);
}
