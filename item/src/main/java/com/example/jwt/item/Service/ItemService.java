package com.example.jwt.item.Service;

import com.example.jwt.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.jwt.item.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemService {
    private ItemRepository itemRepository;
    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }
    public Item getItemById(Long id){
        return itemRepository.findById(id.intValue()).orElseThrow();
    }
    public Item createItem(Item item){
        return itemRepository.save(item);
    }
    public Item updateItem(Long id, Item item){
        Item item1=getItemById(id);
        item.setId(id);
        return itemRepository.save(item);
    }
    public void deleteItem(Long id){
        Item item1=getItemById(id);
        itemRepository.delete(item1);
    }

}