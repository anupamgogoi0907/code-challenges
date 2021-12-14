package com.letscode.starwars.utility;

import com.letscode.starwars.dto.ItemDTO;
import com.letscode.starwars.dto.RelatorioDTO;
import com.letscode.starwars.entity.Item;
import com.letscode.starwars.entity.Rebelde;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppUtility {
    public static final String ITEM_ARMA = "Arma";
    public static final String ITEM_MUNICAO = "Munição";
    public static final String ITEM_AGUA = "Água";
    public static final String ITEM_COMIDA = "Comida";

    public static Integer getPontosByItem(String item) {
        if (item != null && !item.isEmpty()) {
            if (item.equalsIgnoreCase(ITEM_ARMA)) {
                return 4;
            } else if (item.equalsIgnoreCase(ITEM_MUNICAO)) {
                return 3;
            } else if (item.equalsIgnoreCase(ITEM_AGUA)) {
                return 2;
            } else if (item.equalsIgnoreCase(ITEM_COMIDA)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * The Rebelde must have the items he/she is vending (trading) in  inventory (Database)
     *
     * @param existingItems items he/she has in inventory
     * @param tradingItems  items he/she is trading.
     * @return
     */
    public static List<Item> validateItems(List<Item> existingItems, List<ItemDTO> tradingItems) {
        List<Item> tradableItems = new ArrayList<>();
        tradingItems.forEach(vi -> {
            String nome = vi.getNome();
            int qty = vi.getQuantidade();

            existingItems.forEach(ei -> {
                if (nome.equals(ei.getNome()) && qty <= ei.getQuantidade()) {
                    Item newItem = new Item()
                            .setId(ei.getId())
                            .setNome(nome)
                            .setQuantidade(qty)
                            .setPontos(qty * getPontosByItem(nome));
                    tradableItems.add(newItem);
                }
            });
        });
        return tradableItems;
    }

    /**
     * The sum of all pontos between two trades must be equal.
     *
     * @param items1
     * @param items2
     * @return
     */
    public static boolean validatePontos(List<Item> items1, List<Item> items2) {
        int pontos1 = 0;
        int pontos2 = 0;
        for (Item item : items1) {
            pontos1 = pontos1 + getPontosByItem(item.getNome()) * item.getQuantidade();
        }
        for (Item item : items2) {
            pontos2 = pontos2 + getPontosByItem(item.getNome()) * item.getQuantidade();
        }
        return pontos1 == pontos2;
    }

    /**
     * Add new items to a Rebelde's existing items
     *
     * @param existingItems Rebelde's existing items in his/her inventory.
     * @param newItems      New items coming from the second Rebelde in the trade.
     * @return
     */
    public static List<Item> addItems(List<Item> existingItems, List<Item> newItems) {
        List<Item> finalItems = new ArrayList<>();
        // nome, item
        Map<String, Item> map = new HashMap<>();
        existingItems.forEach(ei -> {
            map.put(ei.getNome(), ei);
        });
        newItems.forEach(ni -> {
            Item item = map.get(ni.getNome());
            if (item == null) {
                map.put(ni.getNome(), ni);
            } else {
                item.setQuantidade(item.getQuantidade() + ni.getQuantidade());
                item.setPontos(item.getQuantidade() * AppUtility.getPontosByItem(item.getNome()));
                map.put(item.getNome(), item);
            }
        });
        finalItems = map.values().stream().collect(Collectors.toList());
        return finalItems;
    }

    /**
     * Check if an item is present.
     *
     * @param nome
     * @param list
     * @return
     */
    public static Item checkIfItemPresent(String nome, List<Item> list) {
        for (Item item : list) {
            if (nome.equalsIgnoreCase(item.getNome())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Remove the items that the Rebelde traded with the another Rebelde
     *
     * @param existingItems The items the Rebelde has in his/her inventory.
     * @param toRemoveItems The items the Rebelde trades with the another Rebelde.
     * @return
     */
    public static List<Item> removeItems(List<Item> existingItems, List<Item> toRemoveItems) {
        List<Item> finalItems = new ArrayList<>();
        existingItems.forEach(ei -> {
            String nome = ei.getNome();
            int qty = ei.getQuantidade();
            Item check = checkIfItemPresent(nome, toRemoveItems);
            if (check != null) {
                int temp = qty - check.getQuantidade();
                if (temp > 0) {
                    ei.setQuantidade(temp);
                    ei.setPontos(temp * getPontosByItem(nome));
                    finalItems.add(ei);
                }
            } else {
                finalItems.add(ei);
            }
        });
        return finalItems;
    }

    /**
     * Returns common [0] , toberemove [1] and tobeaded[2]
     *
     * @param first
     * @param second
     * @return
     */
    public static List<Item>[] getCommonToRemoveToAddItems(List<Item> first, List<Item> second) {
        List<Item>[] arr = new List[3];
        List<Item> commonList = new ArrayList<>();
        List<Item> toRemoveList = new ArrayList<>();
        List<Item> toAddList = new ArrayList<>();

        first.forEach(fi -> {
            Item check = checkIfItemPresent(fi.getNome(), second);
            if (check != null) {
                commonList.add(check);
            } else {
                toRemoveList.add(fi);
            }
        });

        second.forEach(si -> {
            if (checkIfItemPresent(si.getNome(), commonList) == null) {
                toAddList.add(si);
            }
        });

        arr[0] = commonList;
        arr[1] = toRemoveList;
        arr[2] = toAddList;
        return arr;
    }

    /**
     * Quantidade média de cada tipo de recurso por rebelde (Ex: 2 armas por rebelde).
     *
     * @param listRebelde
     * @return
     */
    public static Map<String, Integer> calculateStats(List<Rebelde> listRebelde) {
        RelatorioDTO relatorioDTO = new RelatorioDTO();
        int rebeldesWithArma = 0;
        int rebeldesWithMunicao = 0;
        int rebeldesWithAgua = 0;
        int rebeldesWithComida = 0;

        Map<String, Integer> mapItemCount = new HashMap<>();
        for (Rebelde r : listRebelde) {
            if (!r.getInventario().getItens().isEmpty()) {
                for (Item item : r.getInventario().getItens()) {
                    if (AppUtility.ITEM_ARMA.equalsIgnoreCase(item.getNome())) {
                        rebeldesWithArma = rebeldesWithArma + 1;
                    }
                    if (AppUtility.ITEM_MUNICAO.equalsIgnoreCase(item.getNome())) {
                        rebeldesWithMunicao = rebeldesWithMunicao + 1;
                    }
                    if (AppUtility.ITEM_AGUA.equalsIgnoreCase(item.getNome())) {
                        rebeldesWithAgua = rebeldesWithAgua + 1;
                    }
                    if (AppUtility.ITEM_COMIDA.equalsIgnoreCase(item.getNome())) {
                        rebeldesWithComida = rebeldesWithComida + 1;
                    }
                    Integer count = mapItemCount.get(item.getNome());
                    if (count == null) {
                        count = 0;
                    }
                    count = count + item.getQuantidade();
                    mapItemCount.put(item.getNome(), count);
                }
            }
        }

        Map<String, Integer> media = new HashMap<>();
        for (Map.Entry<String, Integer> entry : mapItemCount.entrySet()) {
            String key = entry.getKey();
            Integer total = entry.getValue();

            if (ITEM_ARMA.equalsIgnoreCase(key)) {
                media.put(ITEM_ARMA, total / rebeldesWithArma);
            }
            if (ITEM_MUNICAO.equalsIgnoreCase(key)) {
                media.put(ITEM_MUNICAO, total / rebeldesWithMunicao);
            }
            if (ITEM_AGUA.equalsIgnoreCase(key)) {
                media.put(ITEM_AGUA, total / rebeldesWithAgua);
            }
            if (ITEM_COMIDA.equalsIgnoreCase(key)) {
                media.put(ITEM_COMIDA, total / rebeldesWithComida);
            }
        }

        return media;
    }

    /**
     * 1. Porcentagem de traidores.
     * 2. Porcentagem de rebeldes.
     *
     * @param listRebelde
     * @return
     */
    public static double[] calculatePercentage(List<Rebelde> listRebelde) {
        double[] arr = new double[2];
        Integer total = listRebelde.size();
        int countTraidor = 0;
        for (Rebelde r : listRebelde) {
            if (r.isTraidor()) {
                countTraidor = countTraidor + 1;
            }
        }

        int countRebelde = total - countTraidor;
        double porcentagemRebelde = ((double) countRebelde / total) * 100;
        double porcentagemTraidor = ((double) countTraidor / total) * 100;

        arr[0] = porcentagemRebelde;
        arr[1] = porcentagemTraidor;
        return arr;
    }

}
