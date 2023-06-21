package com.shopping.controller;

import com.shopping.entity.Item;
import com.shopping.repository.ItemRepository;
import com.shopping.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/item")
public class ItemController {
    private final ItemService itemService;
    ItemRepository itemRepository;

    //전체 데이터 출력하기
    //http://localhost:8989/item/list
    @GetMapping(value = "/list")
    public List<Item> thymeleafExam01(){
        List<Item> items = itemService.findAll();

        return items;
    }

    // 특정 항목만 선택하기
    //http://localhost:8989/item/191
    @GetMapping(value = "/{itemId}")
    public Item thymeleafExam02(@PathVariable("itemId") Long id){
        Item item = itemService.findById(id);
        return item;
    }

    // 중첩 클래스 사용하는 예시
//    @GetMapping("/nested-json")
//    public OuterData getNestedJson(){
//        OuterData.InnerData innerData = new OuterData.InnerData();
//    }
}
