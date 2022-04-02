package com.kingpiggy.study.productordersystem.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.kingpiggy.study.productordersystem.domain.entity.Product;
import com.kingpiggy.study.productordersystem.domain.entity.ProductStock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductStockRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStockRepository productStockRepository;

    @AfterEach
    public void afterEach() {
        productStockRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("주문수량만큼_재고를_차감한다_성공")
    public void decreaseStock() {
        // given
        Integer stock = 10;
        Integer orderQuantity = 10;
        Integer expectedValue = 0;

        Product product = productRepository.save(Product.builder()
            .name("Test")
            .productNo(1000)
            .price(10000)
            .build());

        ProductStock productStock = productStockRepository.save(ProductStock.builder()
            .product(product)
            .stock(stock)
            .build());

        // when
        productStock.decrease(orderQuantity);

        // then
        assertEquals(expectedValue, productStock.getStock());
    }

    @Test
    @DisplayName("주문수량만큼_재고를_차감한다_실패_재고_부족")
    public void decreaseStockFailure() {
        // given
        Integer stock = 5;
        Integer orderQuantity = 10;
        Integer expectedValue = 5;

        Product product = productRepository.save(Product.builder()
            .name("Test")
            .productNo(1000)
            .price(10000)
            .build());

        ProductStock productStock = productStockRepository.save(ProductStock.builder()
            .product(product)
            .stock(stock)
            .build());

        // when
        productStock.decrease(orderQuantity);

        // then
        assertEquals(expectedValue, productStock.getStock());
    }

}
