package com.letscode.starwars.service;

import com.letscode.starwars.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RelatorioServiceTest {
    @Autowired
    RelatorioService relatorioService;

    @Autowired
    RebeldeService rebeldeService;

    @Test
    public void givenInputShouldGenerateRalatorio() {
        RebeldeDTO r1 = helper("A", false);
        RebeldeDTO r2 = helper("B", true);
        RebeldeDTO r3 = helper("C", false);
        rebeldeService.saveRebelde(r1);
        rebeldeService.saveRebelde(r2);
        rebeldeService.saveRebelde(r3);

        RelatorioDTO relatorioDTO = relatorioService.getReport();
        assertNotNull(relatorioDTO.getPorcentagemRebelde());
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
