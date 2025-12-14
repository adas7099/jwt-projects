package com.example.jwt.item;

import com.example.jwt.item.Service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void testGetAllItemsEndpoint() throws Exception {
        when(itemService.getAllItems()).thenReturn(
                Arrays.asList(new Item("Book", "Novel", 10.0, 5))
        );

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Book"));
    }

    @Test
    void testCreateItemEndpoint() throws Exception {
        Item item = new Item("Pen", "Blue ink", 2.0, 10);
        when(itemService.createItem(item)).thenReturn(item);

        mockMvc.perform(post("/items")
                        .contentType("application/json")
                        .content("{\"name\":\"Pen\",\"description\":\"Blue ink\",\"price\":2.0,\"quantity\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pen"));
    }
}