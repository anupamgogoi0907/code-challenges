package com.letscode.starwars.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letscode.starwars.dto.InventarioDTO;
import com.letscode.starwars.dto.ItemDTO;
import com.letscode.starwars.dto.LocalizacaoDTO;
import com.letscode.starwars.dto.RebeldeDTO;
import com.letscode.starwars.dto.response.AppResponse;
import com.letscode.starwars.repository.RebeldeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class APITest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    RebeldeRepository rebeldeRepository;

    @Test
    public void givenInputShouldCreateRebelde() throws Exception {
        RebeldeDTO rebeldeDTO = helper("Anupam");
        mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(rebeldeDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnRebeldes() throws Exception {
        RebeldeDTO rebeldeDTO = helper("Anupam");
        mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(rebeldeDTO)))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rebelde"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty());
        System.out.println();
    }

    @Test
    public void givenInputShouldCreateAndThenFindRebelde() throws Exception {
        RebeldeDTO rebeldeDTO = helper("Anupam");
        ResultActions resultActions = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(rebeldeDTO)))
                .andExpect(status().isCreated());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        AppResponse appResponse = objectMapper.readValue(contentAsString, AppResponse.class);
        Integer id = (Integer) appResponse.getResponse();

        // Check
        mockMvc.perform(get("/rebelde/" + id)).andExpect(status().isFound());
        System.out.println();
    }

    @Test
    public void givenInputShouldModifyLocal() throws Exception {
        RebeldeDTO rebeldeDTO = helper("Anupam");
        ResultActions resultActions = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(rebeldeDTO)))
                .andExpect(status().isCreated());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        AppResponse appResponse = objectMapper.readValue(contentAsString, AppResponse.class);
        Integer id = (Integer) appResponse.getResponse();

        LocalizacaoDTO localizacaoDTO = new LocalizacaoDTO().setNome("Galaxy2").setLatitude("100.0").setLongitude("200.0");


        mockMvc.perform(patch("/rebelde/" + id + "/reportar/local")
                .contentType("application/json").content(objectMapper.writeValueAsBytes(localizacaoDTO)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void givenInputShouldReportTraidor() throws Exception {
        RebeldeDTO r1 = helper("A");
        RebeldeDTO r2 = helper("B");
        RebeldeDTO r3 = helper("C");
        RebeldeDTO r4 = helper("D");
        mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r1)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r2)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r3)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r4)))
                .andExpect(status().isCreated());
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
