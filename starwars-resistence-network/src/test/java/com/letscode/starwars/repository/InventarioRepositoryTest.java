package com.letscode.starwars.repository;

import com.letscode.starwars.entity.Inventario;
import com.letscode.starwars.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InventarioRepositoryTest {
    @Autowired
    InventarioRepository inventarioRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void givenInputShouldCreateAnInventario() {
        // Create inventario
        Inventario inventario = new Inventario().setId(1).setItens(helper());
        inventarioRepository.save(inventario);

        List<Inventario> inventarioList = (List<Inventario>) inventarioRepository.findAll();
        assertEquals(1, inventarioList.size());
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
