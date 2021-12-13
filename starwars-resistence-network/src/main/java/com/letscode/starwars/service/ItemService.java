package com.letscode.starwars.service;

import com.letscode.starwars.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    public void updateItems(int inventoryID, List<Item> items);
}
