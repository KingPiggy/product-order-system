package com.kingpiggy.study.productordersystem.domain.repository;

import com.kingpiggy.study.productordersystem.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
