package com.shopping.dto;

import com.shopping.entity.OrderProduct;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderProductDto {
    private String name;
    private int count;
    private int orderPrice;
    private String imageUrl;

    public OrderProductDto(OrderProduct orderProduct, String imageUrl) {
        this.name = orderProduct.getProduct().getName();
        this.count = orderProduct.getCount();
        this.orderPrice = orderProduct.getOrderPrice();
        this.imageUrl = imageUrl;
    }
}
