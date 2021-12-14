package com.letscode.starwars.controller;

import com.letscode.starwars.dto.*;
import com.letscode.starwars.dto.response.AppResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        assertNotEquals(0, list.size());
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

    @Test
    public void givenInputShouldReportTraidor() {
        RebeldeDTO r1 = helper("A");
        RebeldeDTO r2 = helper("B");
        RebeldeDTO r3 = helper("C");
        RebeldeDTO r4 = helper("D");
        rebeldeController.createRebelde(r1);
        rebeldeController.createRebelde(r2);
        rebeldeController.createRebelde(r3);
        rebeldeController.createRebelde(r4);

        ReporterDTO rr1 = new ReporterDTO().setIdReporter(2).setIdTraidor(1);
        ResponseEntity re1 = rebeldeController.reportarTraidor(rr1);
        ReporterDTO rr2 = new ReporterDTO().setIdReporter(3).setIdTraidor(1);
        ResponseEntity re2 = rebeldeController.reportarTraidor(rr2);
        ReporterDTO rr3 = new ReporterDTO().setIdReporter(4).setIdTraidor(1);
        ResponseEntity re3 = rebeldeController.reportarTraidor(rr3);
        String appResponse = (String) ((AppResponse) re3.getBody()).getResponse();
        assertEquals("Rebelde foi marcado como Traidor", appResponse);
    }

    @Test
    public void givenInputShouldTrade() {
        RebeldeDTO r1 = new RebeldeDTO()
                .setNome("A")
                .setTraidor(false)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1).setPontos(4))));

        RebeldeDTO r2 = new RebeldeDTO()
                .setNome("A")
                .setTraidor(false)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Água").setQuantidade(2).setPontos(4))));
        rebeldeController.createRebelde(r1);
        rebeldeController.createRebelde(r2);

        TradeDTO r1Trade = new TradeDTO().setId(1).setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1)));
        TradeDTO r2Trade = new TradeDTO().setId(2).setItens(Arrays.asList(new ItemDTO().setNome("Água").setQuantidade(2)));

        ResponseEntity responseEntity = rebeldeController.trade(new TradeDTO[]{r1Trade, r2Trade});
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    public void givenInputShouldNotTrade(){
        RebeldeDTO r1 = new RebeldeDTO()
                .setNome("A")
                .setTraidor(false)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1).setPontos(4))));

        RebeldeDTO r2 = new RebeldeDTO()
                .setNome("A")
                .setTraidor(false)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Água").setQuantidade(2).setPontos(4))));
        rebeldeController.createRebelde(r1);
        rebeldeController.createRebelde(r2);

        TradeDTO r1Trade = new TradeDTO().setId(1).setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1)));
        TradeDTO r2Trade = new TradeDTO().setId(2).setItens(Arrays.asList(new ItemDTO().setNome("Água").setQuantidade(1)));
        ResponseEntity responseEntity = rebeldeController.trade(new TradeDTO[]{r1Trade, r2Trade});
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
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
