package com.example.jwt.item.Controller;

import com.example.jwt.item.Service.ItemService;
import com.example.jwt.item.entity.Item;
import com.example.jwt.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }
    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable Long id){
        return itemService.getItemById(id);
    }
    @PostMapping("/items")
    public Item createItem(@RequestBody Item item){
        return itemService.createItem(item);

    }
    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable Long id,@RequestBody Item item){
        return itemService.updateItem(id,item);
    }
    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
    }

}
