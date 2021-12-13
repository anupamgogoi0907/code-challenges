package com.letscode.starwars.utility;

import com.letscode.starwars.entity.Inventario;
import com.letscode.starwars.entity.Item;
import com.letscode.starwars.entity.Rebelde;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppUtilityTest {


    @Test
    public void givenInputShouldAddItems() {
        List<Item> existingItems = Arrays.asList(new Item().setNome("Arma").setQuantidade(3).setPontos(12));
        List<Item> newItems = Arrays.asList(new Item().setId(3).setNome("Água").setQuantidade(6).setPontos(12));
        List<Item> result = AppUtility.addItems(existingItems, newItems);
        assertEquals(2, result.size());
    }

    @Test
    public void givenInputShouldRemoveItems() {
        List<Item> existingItems = helper2();
        List<Item> toRemoveItems = Arrays.asList(new Item().setId(3).setNome("Arma").setQuantidade(1).setPontos(4));
        List<Item> result = AppUtility.removeItems(existingItems, toRemoveItems);
        int qty = result.stream().filter(r -> r.getId() == 1).findAny().get().getQuantidade();
        assertEquals(1, qty);
    }

    @Test
    public void givenInputShouldGenerateListsForCommonToRemoveToAddItems() {
        List<Item> first = Arrays.asList(new Item().setNome("A"), new Item().setNome("B"), new Item().setNome("C"));
        List<Item> second = Arrays.asList(new Item().setNome("A"), new Item().setNome("D"), new Item().setNome("F"));
        List<Item>[] arr = AppUtility.getCommonToRemoveToAddItems(first, second);
        List<Item> commonList = arr[0];
        assertEquals(1, commonList.size());
    }

    @Test
    public void givenDataShouldGenerateStats() {
        Item arma1 = new Item().setNome("Arma").setQuantidade(8);
        Item mun1 = new Item().setNome("Munição").setQuantidade(3);
        Rebelde r1 = new Rebelde().setNome("Anupam").setInventario(new Inventario().setItens(Arrays.asList(arma1, mun1)));

        Item arma2 = new Item().setNome("Arma").setQuantidade(4);
        Item mun2 = new Item().setNome("Munição").setQuantidade(9);
        Rebelde r2 = new Rebelde().setNome("Ddd").setInventario(new Inventario().setItens(Arrays.asList(arma2, mun2)));

        Map<String, Integer> media = AppUtility.calculateStats(Arrays.asList(r1, r2));
        System.out.println();
    }

    @Test
    public void test() {
        String r = "1:2:3";
        Integer id = 2;
        String[] arr = r.split(":");
        boolean flad = Arrays.asList(arr).contains(id.toString());
        System.out.println();

    }

    public List<Item> helper1() {
        List<Item> itemList = new ArrayList<>();
        Item arma = new Item().setId(1).setNome("Arma").setQuantidade(1).setPontos(4);
        Item municao = new Item().setId(2).setNome("Municao").setQuantidade(1).setPontos(3);
        return Arrays.asList(arma, municao);
    }

    public List<Item> helper2() {
        List<Item> itemList = new ArrayList<>();
        Item arma = new Item().setId(1).setNome("Arma").setQuantidade(2).setPontos(8);
        Item municao = new Item().setId(2).setNome("Municao").setQuantidade(1).setPontos(3);
        return Arrays.asList(arma, municao);
    }
}
