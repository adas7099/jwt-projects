package com.example.jwt.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.jwt.item.Service.ItemService;
import com.example.jwt.item.entity.Item;
import com.example.jwt.item.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import java.util.*;
@SpringBootTest   // Boots up the full Spring context
@AutoConfigureMockMvc
public class ItemServiceTest {

    private ItemRepository itemRepository;
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository);
    }

    @Test
    void testGetAllItems() {
        List<Item> items = Arrays.asList(new Item("Book", "Novel", 10.0, 5));
        when(itemRepository.findAll()).thenReturn(items);

        List<Item> result = itemService.getAllItems();

        assertEquals(1, result.size());
        assertEquals("Book", result.get(0).getName());
    }

    @Test
    void testCreateItem() {
        Item item = new Item("Pen", "Blue ink", 2.0, 10);
        when(itemRepository.save(item)).thenReturn(item);

        Item result = itemService.createItem(item);

        assertNotNull(result);
        assertEquals("Pen", result.getName());
    }

    @Test
    void testGetItemById() {
        Item item = new Item("Laptop", "Gaming", 1200.0, 2);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item result = itemService.getItemById(1L);

        assertEquals("Laptop", result.getName());
    }

    @Test
    void testDeleteItem() {
        doNothing().when(itemRepository).deleteById(1L);

        itemService.deleteItem(1L);

        verify(itemRepository, times(1)).deleteById(1L);
    }
}
