package com.kingpiggy.study.productordersystem.data.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductVO {

    private Integer productNo;

    private String name;

    private Integer price;

    private Integer stock;

}
