package com.letscode.starwars.repository;

import com.letscode.starwars.entity.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {

    @Query(value = "FROM Item WHERE inventario_id =:inventario_id AND id =:item_id")
    public Optional<Item> findByInventoryAndItemID(Integer inventario_id, Integer item_id);

    @Query(value = "FROM Item WHERE inventario_id =:inventario_id")
    public List<Item> findByInventoryId(Integer inventario_id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM Item WHERE id =:item_id ")
    public void deleteNative(Integer item_id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE Item SET quantidade =:qty, pontos =:pontos WHERE inventario_id =:inventario_id AND id =:item_id")
    public void updateNative(Integer qty, Integer pontos, Integer inventario_id, Integer item_id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO Item(nome,quantidade,pontos,inventario_id) VALUES(:nome,:quantidade,:pontos, :inventario_id )")
    public void saveNative(String nome, Integer quantidade, Integer pontos, Integer inventario_id);
}
