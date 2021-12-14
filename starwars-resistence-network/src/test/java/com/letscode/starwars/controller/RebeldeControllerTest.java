package com.letscode.starwars.controller;

import com.letscode.starwars.dto.InventarioDTO;
import com.letscode.starwars.dto.ItemDTO;
import com.letscode.starwars.dto.LocalizacaoDTO;
import com.letscode.starwars.dto.RebeldeDTO;
import com.letscode.starwars.dto.response.AppResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RebeldeControllerTest {

    @Autowired
    private RebeldeController rebeldeController;

    @Test
    public void givenInputShouldCreateRebelde() {
        RebeldeDTO rebeldeDTO = helper("Anupam");
        ResponseEntity responseEntity = rebeldeController.createRebelde(rebeldeDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void findAllRebeldes() {
        RebeldeDTO r1 = helper("Anupam");
        RebeldeDTO r2 = helper("Xyz");
        rebeldeController.createRebelde(r1);
        rebeldeController.createRebelde(r2);

        ResponseEntity responseEntity = rebeldeController.getRebeldes();
        AppResponse appResponse = (AppResponse) responseEntity.getBody();
        List<RebeldeDTO> list = (List<RebeldeDTO>) appResponse.getResponse();
        assertEquals(2, list.size());
    }

    @Test
    public void givenInputShouldCreateAndThenFindRebelde() {
        RebeldeDTO r1 = helper("Anupam");
        ResponseEntity responseEntity1 = rebeldeController.createRebelde(r1);
        Integer id = (Integer) ((AppResponse) responseEntity1.getBody()).getResponse();
        ResponseEntity responseEntity2 = rebeldeController.findById(id);
        RebeldeDTO temp = (RebeldeDTO) ((AppResponse) responseEntity2.getBody()).getResponse();
        assertEquals("Anupam", temp.getNome());
    }

    @Test
    public void givenInputShouldModifyLocal() {
        RebeldeDTO rebeldeDTO = helper("Anupam");
        ResponseEntity responseEntity = rebeldeController.createRebelde(rebeldeDTO);
        Integer id = (Integer) ((AppResponse) responseEntity.getBody()).getResponse();
        LocalizacaoDTO localizacaoDTO = new LocalizacaoDTO().setNome("Galaxy2").setLatitude("100.0").setLongitude("200.0");
        ResponseEntity rp = rebeldeController.reportarLocal(id, localizacaoDTO);
        assertEquals(HttpStatus.ACCEPTED, rp.getStatusCode());
    }

    private RebeldeDTO helper(String nome) {
        RebeldeDTO rebeldeDTO = new RebeldeDTO()
                .setNome(nome)
                .setTraidor(false)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1).setPontos(4))));
        return rebeldeDTO;
    }
}
