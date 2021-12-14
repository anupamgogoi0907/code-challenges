package com.letscode.starwars.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letscode.starwars.dto.InventarioDTO;
import com.letscode.starwars.dto.ItemDTO;
import com.letscode.starwars.dto.LocalizacaoDTO;
import com.letscode.starwars.dto.RebeldeDTO;
import com.letscode.starwars.repository.RebeldeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
