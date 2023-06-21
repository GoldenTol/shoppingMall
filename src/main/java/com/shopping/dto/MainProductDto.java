package com.shopping.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainProductDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer price;

    @QueryProjection
    public MainProductDto(Long id, String name, String description, String imageUrl, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
