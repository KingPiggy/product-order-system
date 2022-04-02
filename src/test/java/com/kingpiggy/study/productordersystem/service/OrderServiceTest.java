package com.kingpiggy.study.productordersystem.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    private int count = 0;
    private int THREAD_CNT = 10;
    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_CNT);
    private CountDownLatch latch = new CountDownLatch(THREAD_CNT);

    private static final int SUCCESS_COUNT = 7;
    private static final int FAILURE_COUNT = 3;
    private static final Integer PRODUCT_NO_OF_DUMMY_1 = 628066;
    private static final Integer PRODUCT_NO_OF_DUMMY_2 = 778422;
    private static final Integer ORDER_QUANTITY_OF_DUMMY_1 = 1;
    private static final Integer ORDER_QUANTITY_OF_DUMMY_2 = 1;

    @Test
    @DisplayName("주문_Multi_Thread_로_인한_품절_발생_SoldOutException")
    void orderFailure() throws Exception {
        // given
        productService.saveAllProductByVOList();

        Map<Integer, Integer> orderMap = new HashMap<>();
        orderMap.put(PRODUCT_NO_OF_DUMMY_1, ORDER_QUANTITY_OF_DUMMY_1);
        orderMap.put(PRODUCT_NO_OF_DUMMY_2, ORDER_QUANTITY_OF_DUMMY_2);

        // when
        for(int i = 0; i < THREAD_CNT; i++) {
            executorService.execute(() -> {
                try{
                    orderService.order(orderMap);
                    count += 1;
                    latch.countDown();
                } catch (Exception e) {
                    latch.countDown();
                }
            });
        }

        // then
        // 628066의 재고 수는 8, 778422의 재고 수는 7
        // 각각 1개씩 주문할 때 7번 주문에 성공하고, 3번 실패한다.
        latch.await();
        assertAll(
            () -> assertEquals(SUCCESS_COUNT, count),
            () -> assertEquals(FAILURE_COUNT, THREAD_CNT - count)
        );
    }

}
