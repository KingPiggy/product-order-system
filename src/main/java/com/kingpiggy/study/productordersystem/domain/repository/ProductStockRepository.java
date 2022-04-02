package com.kingpiggy.study.productordersystem.domain.repository;

import com.kingpiggy.study.productordersystem.domain.entity.ProductStock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    @Query(
        "SELECT  s " +
        "FROM    ProductStock AS s " +
        "   JOIN FETCH s.product " +
        "WHERE s.product.productNo = :productNo "
    )
    Optional<ProductStock> findByProductNo(@Param("productNo") Integer productNo);

}
