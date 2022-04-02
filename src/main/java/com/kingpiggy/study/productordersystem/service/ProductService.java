package com.kingpiggy.study.productordersystem.service;

import com.kingpiggy.study.productordersystem.data.vo.ProductVO;
import com.kingpiggy.study.productordersystem.domain.entity.Product;
import com.kingpiggy.study.productordersystem.domain.entity.ProductStock;
import com.kingpiggy.study.productordersystem.domain.repository.ProductRepository;
import com.kingpiggy.study.productordersystem.domain.repository.ProductStockRepository;
import com.kingpiggy.study.productordersystem.util.CSVUtil;
import com.kingpiggy.study.productordersystem.web.Header;
import com.kingpiggy.study.productordersystem.web.dto.response.ProductResponseDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final CSVUtil csvUtil;

    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    private static final String WHITE_SPACE_TAP = "    ";
    private static final String WHITE_SPACE_TAP_X4 = "                ";

    @Transactional(rollbackFor = Exception.class)
    public Header saveAllProductByVOList() throws Exception {
        List<ProductVO> voList = csvUtil.getProductVOListByCSV();

        if (voList.isEmpty()) {
            return Header.OK("There's no item to save.");
        }

        for (ProductVO vo : voList) {
            Product product = productRepository.save(Product.builder()
                .productNo(vo.getProductNo())
                .name(vo.getName())
                .price(vo.getPrice())
                .build());

            ProductStock productStock = productStockRepository.save(ProductStock.builder()
                .stock(vo.getStock())
                .product(product)
                .build());
        }

        return Header.OK("Save Done");
    }

    @Transactional(readOnly = true)
    public Map<Integer, Integer> getAllProduct() {
        System.out.print("상품번호" + WHITE_SPACE_TAP);
        System.out.print("상품명" + WHITE_SPACE_TAP_X4);
        System.out.print("판매가격" + WHITE_SPACE_TAP);
        System.out.println("재고수" + WHITE_SPACE_TAP);

        List<ProductResponseDto> responseDtos = productRepository.findAllProductWithStock()
            .stream()
            .map(
                object -> ProductResponseDto.builder()
                    .id((Long) object[0])
                    .productNo((Integer) object[1])
                    .name((String) object[2])
                    .price((Integer) object[3])
                    .stock((Integer) object[4])
                    .build()
            ).collect(Collectors.toList());

        Map<Integer, Integer> productMap = new HashMap<>();

        for(ProductResponseDto dto : responseDtos) {
            System.out.print(dto.getProductNo() + WHITE_SPACE_TAP);
            System.out.print(dto.getName() + WHITE_SPACE_TAP);
            System.out.print(dto.getPrice() + WHITE_SPACE_TAP);
            System.out.println(dto.getStock() + WHITE_SPACE_TAP);

            productMap.put(dto.getProductNo(), dto.getStock());
        }

        System.out.println();

        return productMap;
    }

}
