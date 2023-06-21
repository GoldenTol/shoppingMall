package com.shopping.entity;

import com.shopping.constant.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Getter @Setter @ToString
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, name = "price")
    private Integer price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Integer stock;

    private String image;

    @Lob //CLOB, BLOB
    @Column(nullable = false)
    private String description;

    public Item() {}

    public Item(Long id, String name, Integer price, Category category, Integer stock, String image, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.image = image;
        this.description = description;
    }
}
