package com.kingpiggy.study.productordersystem.service;

import com.kingpiggy.study.productordersystem.domain.entity.Orders;
import com.kingpiggy.study.productordersystem.domain.entity.OrdersItem;
import com.kingpiggy.study.productordersystem.domain.entity.ProductStock;
import com.kingpiggy.study.productordersystem.domain.repository.OrdersItemRepository;
import com.kingpiggy.study.productordersystem.domain.repository.OrdersRepository;
import com.kingpiggy.study.productordersystem.domain.repository.ProductStockRepository;
import com.kingpiggy.study.productordersystem.web.exception.EmptyOrderRequestException;
import com.kingpiggy.study.productordersystem.web.exception.EntityNotFoundException;
import com.kingpiggy.study.productordersystem.web.exception.SoldOutException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private static final int TRANSFER_OCCUR_AMOUNT = 50000;
    private static final int TRANSFER_AMOUNT = 2500;

    private final OrdersRepository ordersRepository;
    private final OrdersItemRepository ordersItemRepository;
    private final ProductStockRepository productStockRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long order(Map<Integer, Integer> orderMap) {
        if(orderMap.isEmpty()) {
            throw new EmptyOrderRequestException("Empty order requested.");
        }

        Orders orders = ordersRepository.save(Orders.builder().build());

        List<OrdersItem> ordersItemList = new ArrayList<>();
        for (Entry<Integer, Integer> entrySet : orderMap.entrySet()) {
            Integer productNo = entrySet.getKey();
            Integer quantity = entrySet.getValue();

            ProductStock productStock = productStockRepository.findByProductNo(productNo)
                .orElseThrow(() -> new EntityNotFoundException("Product stock not found."));

            if(productStock.getStock() < quantity) {
                throw new SoldOutException("Product sold out");
            }

            productStock.decrease(quantity);

            ordersItemList.add(ordersItemRepository.save(OrdersItem.builder()
                .orders(orders)
                .product(productStock.getProduct())
                .quantity(quantity)
                .build()));
        }

        int totalOrdersAmount = ordersItemList
            .stream()
            .mapToInt(ordersItem -> ordersItem.getProduct().getPrice() * ordersItem.getQuantity()).sum();

        int transferAmount = 0;
        if(totalOrdersAmount < TRANSFER_OCCUR_AMOUNT) {
            transferAmount = TRANSFER_AMOUNT;
        }

        orders.updateOrders(totalOrdersAmount, transferAmount, LocalDateTime.now());

        return orders.getId();
    }

    @Transactional(readOnly = true)
    public void printOrderHistory(Long ordersId) {
        Orders orders = ordersRepository.findById(ordersId)
            .orElseThrow(() -> new EntityNotFoundException("Orders not found."));
        List<OrdersItem> ordersItemList = ordersItemRepository.findAllByOrders(orders);

        DecimalFormat decFormat = new DecimalFormat("###,###");
        int totalOrderAmount = orders.getTotalOrdersAmount();
        int transferAmount = orders.getTransferAmount();

        System.out.println("주문 내역:");
        System.out.println("----------------------------------");
        for(OrdersItem item : ordersItemList) {
            System.out.println(item.getProduct().getName() + " - " + item.getQuantity() + "개");
        }
        System.out.println("----------------------------------");
        System.out.println("주문금액: " + decFormat.format(totalOrderAmount) + "원");
        if(transferAmount != 0) {
            System.out.println("배송금액: " + decFormat.format(transferAmount) + "원");
        }
        System.out.println("----------------------------------");
        System.out.println("지불금액: " + decFormat.format((totalOrderAmount + transferAmount)) + "원");
        System.out.println("----------------------------------");
        System.out.println();
    }

}
