package com.letscode.starwars.repository;


import com.letscode.starwars.entity.Rebelde;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RebeldeRepository extends CrudRepository<Rebelde, Integer> {


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Rebelde set reporters =:reporters  where id =:id")
    public void updateReporters(Integer id, String reporters);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Rebelde set traidor =:traidor  where id =:id")
    public void updateTraidor(Integer id, boolean traidor);

}
