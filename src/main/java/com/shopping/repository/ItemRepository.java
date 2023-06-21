package com.shopping.repository;

import com.shopping.constant.Category;
import com.shopping.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findItemByCategoryLike(Category category);

}
