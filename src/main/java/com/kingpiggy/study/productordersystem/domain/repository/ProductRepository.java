package com.kingpiggy.study.productordersystem.domain.repository;

import com.kingpiggy.study.productordersystem.domain.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
        "SELECT  p.id, p.productNo, p.name, p.price, s.stock " +
        "FROM    Product AS p" +
        "   JOIN ProductStock AS s ON s.product = p " +
        "ORDER BY p.productNo DESC "
    )
    List<Object[]> findAllProductWithStock();

}
