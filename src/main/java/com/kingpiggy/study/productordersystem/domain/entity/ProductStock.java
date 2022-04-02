package com.kingpiggy.study.productordersystem.domain.entity;

import com.kingpiggy.study.productordersystem.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductStock extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stock;

    @OneToOne(fetch = FetchType.LAZY)
    private Product product;

    public void decrease(Integer quantity) {
        if(this.stock >= quantity) {
            this.stock -= quantity;
        }
    }

}
