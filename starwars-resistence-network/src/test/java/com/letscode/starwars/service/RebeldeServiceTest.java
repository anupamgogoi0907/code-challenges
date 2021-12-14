package com.letscode.starwars.service;


import com.letscode.starwars.dto.*;
import com.letscode.starwars.entity.Rebelde;
import com.letscode.starwars.repository.RebeldeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class RebeldeServiceTest {


    @Autowired
    private RebeldeService rebeldeService;

    @Autowired
    private RebeldeRepository rebeldeRepository;

    @Test
    public void givenInputShouldCreateRebelde() {
        RebeldeDTO r1 = helper("Anupam", false);
        Integer id = rebeldeService.saveRebelde(r1);
        assertNotEquals(0, id);
    }

    @Test
    public void shouldReturnAllRebeldes() {
        RebeldeDTO r1 = helper("Anupam", false);
        RebeldeDTO r2 = helper("Abc", false);
        rebeldeService.saveRebelde(r1);
        rebeldeService.saveRebelde(r2);
        List<RebeldeDTO> list = rebeldeService.getAllRebeldes();
        assertEquals(2, list.size());
    }

    @Test
    public void givenIdShouldReturnTheRebelde() {
        RebeldeDTO r1 = helper("Anupam", false);
        Integer id = rebeldeService.saveRebelde(r1);
        assertNotEquals(0, id);
        RebeldeDTO rebeldeDTO = rebeldeService.findById(id);
        assertEquals(id, rebeldeDTO.getId());
    }

    @Test
    public void givenInputShouldUpdateReporters() {
        RebeldeDTO r1 = helper("A", false);
        RebeldeDTO r2 = helper("B", false);
        RebeldeDTO r3 = helper("C", false);

        int id1 = rebeldeService.saveRebelde(r1);
        int id2 = rebeldeService.saveRebelde(r2);
        int id3 = rebeldeService.saveRebelde(r3);

        ReporterDTO reporter1 = new ReporterDTO().setIdReporter(id2).setIdTraidor(id1);
        rebeldeService.updateReporters(reporter1);

        ReporterDTO reporter2 = new ReporterDTO().setIdReporter(id3).setIdTraidor(id1);
        rebeldeService.updateReporters(reporter2);

        Optional<Rebelde> r1Entity = rebeldeRepository.findById(id1);
        String reporters = r1Entity.get().getReporters();
        assertEquals(id2 + ":" + id3, reporters);
    }

    private RebeldeDTO helper(String nome, boolean traidor) {
        RebeldeDTO rebeldeDTO = new RebeldeDTO()
                .setNome(nome)
                .setTraidor(traidor)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1).setPontos(4))));
        return rebeldeDTO;
    }
}
