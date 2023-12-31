package com.shopping.entity;

import com.shopping.constant.ProductStatus;
import com.shopping.dto.ProductFormDto;
import com.shopping.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Getter @Setter @ToString
public class Product extends BaseEntity {

    @Id
    @Column(name = "products_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id ;

    @Column(nullable = false, length = 50)
    private String name ;

    @Column(nullable = false, name= "price")
    private Integer price ;

    @Column(nullable = false)
    private Integer stock ;

    @Lob // CLOB, BLOB
    @Column(nullable = false)
    private String description ;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus ;

    public void updateProduct(ProductFormDto productFormDto){
        this.name=productFormDto.getName();
        this.price=productFormDto.getPrice();
        this.stock=productFormDto.getStock();
        this.description=productFormDto.getDescription();
        this.productStatus=productFormDto.getProductStatus();
    }

    public void removeStock(int vstock){
        int restStock=this.stock-vstock;
        if(restStock<0){
            String message = "상품의 재고가 부족합니다. 현재 재고 수량은 '"+this.stock+"'개 입니다.";
            throw new OutOfStockException(message);
        }
        this.stock=restStock;
    }

    public void addStock(int vstock){
        this.stock += vstock;
    }
}
