package com.kingpiggy.study.productordersystem.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.kingpiggy.study.productordersystem.domain.entity.Orders;
import com.kingpiggy.study.productordersystem.domain.entity.OrdersItem;
import com.kingpiggy.study.productordersystem.domain.entity.Product;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrdersItemRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersItemRepository ordersItemRepository;

    @AfterEach
    public void afterEach() {
        ordersItemRepository.deleteAll();
        ordersRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("주문_엔티티를_통한_주문_아이템_목록_조회_성공")
    void findAllByOrders() {
        // given
        Integer expectedValue = 2;

        Product product = productRepository.save(Product.builder()
            .name("Test")
            .productNo(1000)
            .price(10000)
            .build());

        Product product2 = productRepository.save(Product.builder()
            .name("Test2")
            .productNo(2000)
            .price(20000)
            .build());

        Orders orders = ordersRepository.save(Orders.builder().build());

        OrdersItem ordersItem = ordersItemRepository.save(OrdersItem.builder()
            .orders(orders)
            .product(product)
            .quantity(10)
            .build());

        OrdersItem ordersItem2 = ordersItemRepository.save(OrdersItem.builder()
            .orders(orders)
            .product(product2)
            .quantity(20)
            .build());

        // when
        List<OrdersItem> result = ordersItemRepository.findAllByOrders(orders);

        // then
        assertAll(
            () -> assertEquals(expectedValue, result.size()),
            () -> assertEquals(ordersItem2.getId(), result.get(0).getId())
        );
    }

}
