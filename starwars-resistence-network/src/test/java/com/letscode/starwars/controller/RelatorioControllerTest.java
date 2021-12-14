package com.letscode.starwars.controller;

import com.letscode.starwars.dto.InventarioDTO;
import com.letscode.starwars.dto.ItemDTO;
import com.letscode.starwars.dto.LocalizacaoDTO;
import com.letscode.starwars.dto.RebeldeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RelatorioControllerTest {
    @Autowired
    RelatorioController relatorioController;

    @Autowired
    RebeldeController rebeldeController;

    @Test
    public void givenInputShouldGenerateRelatorio() {
        RebeldeDTO r1 = helper("A", false);
        RebeldeDTO r2 = helper("B", true);
        RebeldeDTO r3 = helper("C", false);
        rebeldeController.createRebelde(r1);
        rebeldeController.createRebelde(r2);
        rebeldeController.createRebelde(r3);

        ResponseEntity responseEntity = relatorioController.getReport();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
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
