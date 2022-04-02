package com.kingpiggy.study.productordersystem.domain.repository;

import com.kingpiggy.study.productordersystem.domain.entity.Orders;
import com.kingpiggy.study.productordersystem.domain.entity.OrdersItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrdersItemRepository extends JpaRepository<OrdersItem, Long> {

    @Query(
        "SELECT o " +
        "FROM   OrdersItem AS o " +
        "    JOIN FETCH o.orders " +
        "    JOIN FETCH o.product " +
        "WHERE  o.orders = :orders " +
        "ORDER BY o.quantity DESC "
    )
    List<OrdersItem> findAllByOrders(@Param("orders") Orders orders);

}
