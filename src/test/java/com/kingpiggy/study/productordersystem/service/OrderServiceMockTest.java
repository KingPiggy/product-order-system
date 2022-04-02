package com.kingpiggy.study.productordersystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.kingpiggy.study.productordersystem.domain.entity.Product;
import com.kingpiggy.study.productordersystem.domain.entity.ProductStock;
import com.kingpiggy.study.productordersystem.domain.repository.OrdersItemRepository;
import com.kingpiggy.study.productordersystem.domain.repository.OrdersRepository;
import com.kingpiggy.study.productordersystem.domain.repository.ProductRepository;
import com.kingpiggy.study.productordersystem.domain.repository.ProductStockRepository;
import com.kingpiggy.study.productordersystem.web.exception.EmptyOrderRequestException;
import com.kingpiggy.study.productordersystem.web.exception.EntityNotFoundException;
import com.kingpiggy.study.productordersystem.web.exception.SoldOutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceMockTest {

    private static final int TRANSFER_OCCUR_AMOUNT = 50000;
    private static final int TRANSFER_AMOUNT = 2500;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrdersItemRepository ordersItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductStockRepository productStockRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문_실패_주문한_상품이_없음_EmptyOrderRequestException")
    void orderFailure1() {
        // given
        Map<Integer, Integer> orderMap = new HashMap<>();

        // when

        // then
        assertThrows(EmptyOrderRequestException.class, () -> orderService.order(orderMap));
    }

    @Test
    @DisplayName("주문_실패_주문할_상품의_재고_엔티티가_없음_EntityNotFoundException")
    void orderFailure2() {
        // given
        Map<Integer, Integer> orderMap = new HashMap<>();
        Integer productNo = 1000;
        Integer orderQuantity = 1;
        orderMap.put(productNo, orderQuantity);

        // when
        when(productStockRepository.findByProductNo(productNo)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> orderService.order(orderMap));
    }

    @Test
    @DisplayName("주문_실패_주문할_상품의_재고가_부족_SoldOutException")
    void orderFailure3() {
        // given
        Map<Integer, Integer> orderMap = new HashMap<>();
        Integer productNo = 1000;
        Integer orderQuantity = 10;
        orderMap.put(productNo, orderQuantity);

        Integer stock = 1;
        Product product = productRepository.save(Product.builder()
            .name("Test")
            .productNo(productNo)
            .price(10000)
            .build());

        ProductStock productStock = ProductStock.builder()
            .product(product)
            .stock(stock)
            .build();

        // when
        when(productStockRepository.findByProductNo(productNo)).thenReturn(Optional.of(productStock));

        // then
        assertThrows(SoldOutException.class, () -> orderService.order(orderMap));
    }

    @Test
    @DisplayName("주문_내역_조회_실패_주문_엔티티가_없음_EntityNotFoundException")
    void printOrderHistoryFailure() {
        // given
        Long ordersId = 1L;

        // when
        when(ordersRepository.findById(ordersId)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> orderService.printOrderHistory(ordersId));
    }


}
