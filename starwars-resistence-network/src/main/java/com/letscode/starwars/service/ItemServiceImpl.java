package com.letscode.starwars.service;

import com.letscode.starwars.entity.Item;
import com.letscode.starwars.exception.StarwarsException;
import com.letscode.starwars.repository.ItemRepository;
import com.letscode.starwars.utility.AppUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    ItemRepository itemRepository;

    @Override
    public void updateItems(int inventoryID, List<Item> newItems) {
        try {
            logger.debug("Executing " + this.getClass().getName() + ".updateItems()");
            List<Item> existingItems = itemRepository.findByInventoryId(inventoryID);

            List<Item>[] arr = AppUtility.getCommonToRemoveToAddItems(existingItems, newItems);
            List<Item> common = arr[0];
            List<Item> toRemove = arr[1];
            List<Item> toAdd = arr[2];

            if (!common.isEmpty()) {
                common.forEach(item -> {
                    itemRepository.updateNative(inventoryID, item.getId(), item.getQuantidade(), item.getPontos());
                });
            }
            if (!toRemove.isEmpty()) {
                toRemove.forEach(item -> {
                    itemRepository.deleteNative(item.getId());
                });
            }
            if (!toAdd.isEmpty()) {
                toAdd.forEach(item -> {
                    itemRepository.saveNative(item.getNome(), item.getQuantidade(), item.getPontos(), inventoryID);
                });
            }

        } catch (Exception e) {
            throw new StarwarsException.InternalServerException(e.getMessage());
        }

    }
}
