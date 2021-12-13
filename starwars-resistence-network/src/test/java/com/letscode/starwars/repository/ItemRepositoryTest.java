package com.letscode.starwars.repository;


import com.letscode.starwars.entity.Inventario;
import com.letscode.starwars.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    InventarioRepository inventarioRepository;

    @Test
    public void givenInputShouldCreateAnItem() {
        // Create
        Item item1 = new Item().setNome("Arma").setQuantidade(1).setPontos(4);
        itemRepository.save(item1);

        // Check
        Optional<Item> temp = itemRepository.findById(item1.getId());
        assertEquals("Arma", temp.get().getNome());
    }

    @Test
    public void givenNomeOfTheItemShouldBeUpdated() {
        // Create an item
        Item item1 = new Item().setNome("Arma").setQuantidade(1).setPontos(3);
        itemRepository.save(item1);

        // Update its value
        item1.setPontos(10);
        itemRepository.save(item1);

        // Check if changed.
        Optional<Item> temp = itemRepository.findById(item1.getId());
        assertEquals(10, temp.get().getPontos());
    }

    @Test
    public void givenItemShouldBeDeleted() {
        // Create an item
        Item item1 = new Item().setNome("Arma").setQuantidade(1).setPontos(3);
        itemRepository.save(item1);

        // Generated id.
        int id = item1.getId();

        // Delete
        itemRepository.delete(item1);

        // Check.
        Optional<Item> temp = itemRepository.findById(id);
        assertEquals(false, temp.isPresent());
    }

    @Test
    public void givenQueryShouldFindTheItem() {
        // Create inventario
        Inventario inventario = new Inventario().setItens(helper());
        inventarioRepository.save(inventario);
        Optional<Item> item = itemRepository.findByInventoryAndItemID(inventario.getId(), 1);
        assertNotNull(item);
    }

    @Test
    public void givenInvetarioIdShouldReturnAllItems() {
        Inventario inventario = new Inventario().setItens(helper());
        inventarioRepository.save(inventario);
        List<Item> items = itemRepository.findByInventoryId(inventario.getId());
        assertEquals(2, items.size());
    }

    @Test
    public void givenDataShouldBeSavedByNativeSaveQuery() {
        Inventario inventario = new Inventario().setItens(helper());
        inventarioRepository.save(inventario);

        itemRepository.saveNative("A", 1, 2, inventario.getId());
        List<Item> items = itemRepository.findByInventoryId(inventario.getId());
        assertEquals(3, items.size());
    }

    @Test
    public void givenDataShouldBeModifiedByNativeUpdate() {
        Inventario inventario = new Inventario().setItens(helper());
        inventarioRepository.save(inventario);

        itemRepository.updateNative(100, 1000, inventario.getId(), 1);
        List<Item> items = itemRepository.findByInventoryId(inventario.getId());
        System.out.println();
    }

    @Test
    public void deleteNative() {
        Inventario inventario = new Inventario().setItens(helper());
        inventarioRepository.save(inventario);

        itemRepository.deleteNative(1);
        List<Item> items = itemRepository.findByInventoryId(inventario.getId());
    }

    public List<Item> helper() {
        List<Item> itens = Arrays.asList(new Item()
                        .setNome("Arma")
                        .setQuantidade(1)
                        .setPontos(4),
                new Item()
                        .setNome("Munição")
                        .setQuantidade(2)
                        .setPontos(6));
        return itens;
    }
}
