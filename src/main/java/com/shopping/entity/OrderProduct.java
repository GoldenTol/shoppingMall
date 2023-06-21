package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderProduct extends BaseEntity {
    @Id
    @Column(name = "order_product_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격
    private int count; // 수량

    public static OrderProduct createOrderProduct(Product product, int count){
        OrderProduct orderProduct=new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setCount(count);
        orderProduct.setOrderPrice(product.getPrice());

        product.removeStock(count);

        return orderProduct;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }

    public void cancle(){
        this.getProduct().addStock(count);
    }
}
