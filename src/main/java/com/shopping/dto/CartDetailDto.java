package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDetailDto {
    private Long cartProductId;
    private String name;
    private int price;
    private int count;
    private String imageUrl;

    public CartDetailDto(Long cartProductId, String name, int price, int count, String imageUrl) {
        this.cartProductId = cartProductId;
        this.name = name;
        this.price = price;
        this.count = count;
        this.imageUrl = imageUrl;
    }
}
