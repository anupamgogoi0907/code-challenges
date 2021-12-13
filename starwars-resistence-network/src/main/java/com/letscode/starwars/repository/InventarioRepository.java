package com.letscode.starwars.repository;


import com.letscode.starwars.entity.Inventario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends CrudRepository<Inventario, Integer> {
}
