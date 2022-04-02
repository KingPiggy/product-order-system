package com.kingpiggy.study.productordersystem.domain.entity;

import com.kingpiggy.study.productordersystem.domain.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalOrdersAmount;

    private Integer transferAmount;

    private LocalDateTime orderDate;

    public void updateOrders(Integer totalOrdersAmount, Integer transferAmount, LocalDateTime orderDate) {
        this.totalOrdersAmount = totalOrdersAmount;
        this.transferAmount = transferAmount;
        this.orderDate = orderDate;
    }

}
