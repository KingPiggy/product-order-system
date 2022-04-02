package com.kingpiggy.study.productordersystem.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;

    private Integer productNo;

    private String name;

    private Integer price;

    private Integer stock;

}
