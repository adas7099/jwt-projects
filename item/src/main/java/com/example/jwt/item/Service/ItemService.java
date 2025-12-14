package com.example.jwt.item.Service;

import com.example.jwt.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.jwt.item.entity.Item;

import java.util.List;

public class ItemService {

    private ItemRepository itemRepository;
    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    List<Item> getAllItems();
    Item getItemById(Long id);
    Item createItem(Item item);
    Item updateItem(Long id, Item item);
    void deleteItem(Long id);

}