package com.shopping.repository;

import com.shopping.common.GenerateItemData;
import com.shopping.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 추가 테스트")
    public void addManyItemInsertTest(){
        List<Item> itemList = GenerateItemData.createItemMany();
        for(Item item:itemList){
            itemRepository.save(item);
        }
    }
}