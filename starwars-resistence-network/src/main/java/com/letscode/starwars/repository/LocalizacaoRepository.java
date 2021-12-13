package com.letscode.starwars.repository;


import com.letscode.starwars.entity.Localizacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacaoRepository extends CrudRepository<Localizacao, Integer> {
}
