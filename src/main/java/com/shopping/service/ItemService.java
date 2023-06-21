package com.shopping.service;

import com.shopping.entity.Item;
import com.shopping.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        List<Item> itemViews = itemRepository.findAll();

        return itemViews;
    }

    public Item findById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return item;
    }

//    public List<Item> findPageables(Pageable pageable){
//        List<Item> items = itemRepository.findItems(pageable);
//
//        return items;
//    }
}
