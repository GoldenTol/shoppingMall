package com.shopping.dto;

import com.shopping.constant.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSearchDto {
    private String searchDateType; // 기간 조회
    private ProductStatus productStatus; // 판매상태 조회
    private String searchBy; // 상품 유형 조회
    private String searchQuery; // 키워드 조회
}
