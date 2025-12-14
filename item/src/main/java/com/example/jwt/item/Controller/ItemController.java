package com.example.jwt.item.Controller;

import com.example.jwt.item.entity.Item;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ItemController {

    public List<Item> getAllItems();
    public Item getItemById(Long id);
    public Item createItem(Item item);
    public Item updateItem(Long id, Item item);
    public void deleteItem(Long id);

}
