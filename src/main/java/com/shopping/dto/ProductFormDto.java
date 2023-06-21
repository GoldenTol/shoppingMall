package com.shopping.dto;

import com.shopping.constant.ProductStatus;
import com.shopping.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProductFormDto {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "상품가격은 필수 입력 값입니다.")
    private Integer price; 

    @NotBlank(message = "상품 상세설명은 필수 입력 값입니다.")
    private String description;

    @NotNull(message = "상품재고는 필수 입력 값입니다.")
    private Integer stock;
    
    private ProductStatus productStatus;

    private List<ProductImageDto> productImageDtoList=new ArrayList<>();
    // 사진은 상품 1개당 최대 5개까지 들어갈 수 있기 때문에 List collection이 됩니다.
    private List<Long>productImageIds=new ArrayList<>();
    // 이미지들의 id를 저장할 컬렉션입니다. (이미지 수정 시 필요합니다.)

    private static ModelMapper modelMapper=new ModelMapper();

    public Product createProduct(){
        return modelMapper.map(this, Product.class);
    }

    public static ProductFormDto of(Product product){
        return modelMapper.map(product, ProductFormDto.class);
    }
}
