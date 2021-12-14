package com.letscode.starwars.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letscode.starwars.dto.*;
import com.letscode.starwars.dto.response.AppResponse;
import com.letscode.starwars.entity.Rebelde;
import com.letscode.starwars.repository.RebeldeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class APITest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RebeldeRepository rebeldeRepository;
    @Autowired
    private ObjectMapper objectMapper;

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
        Integer id = (Integer) getResponseFromResult(resultActions);
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
        ResultActions ra1 = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r1)))
                .andExpect(status().isCreated());
        Integer id1 = (Integer) getResponseFromResult(ra1);

        ResultActions ra2 = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r2)))
                .andExpect(status().isCreated());
        Integer id2 = (Integer) getResponseFromResult(ra2);
        ResultActions ra3 = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r3)))
                .andExpect(status().isCreated());
        Integer id3 = (Integer) getResponseFromResult(ra3);
        ResultActions ra4 = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r4)))
                .andExpect(status().isCreated());
        Integer id4 = (Integer) getResponseFromResult(ra4);

        ReporterDTO rr1 = new ReporterDTO().setIdReporter(id2).setIdTraidor(id1);
        mockMvc.perform(post("/rebelde/reportar/traidor").contentType("application/json").content(objectMapper.writeValueAsBytes(rr1))).andExpect(status().isAccepted());

        ReporterDTO rr2 = new ReporterDTO().setIdReporter(id3).setIdTraidor(id1);
        mockMvc.perform(post("/rebelde/reportar/traidor").contentType("application/json").content(objectMapper.writeValueAsBytes(rr2))).andExpect(status().isAccepted());

        ReporterDTO rr3 = new ReporterDTO().setIdReporter(id4).setIdTraidor(id1);
        mockMvc.perform(post("/rebelde/reportar/traidor").contentType("application/json").content(objectMapper.writeValueAsBytes(rr3))).andExpect(status().isAccepted());

        Optional<Rebelde> temp = rebeldeRepository.findById(id1);
        assertTrue(temp.get().isTraidor());
    }

    @Test
    public void givenInputShouldTrade() throws Exception {
        RebeldeDTO r1 = new RebeldeDTO()
                .setNome("A")
                .setTraidor(false)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1).setPontos(4))));
        ResultActions ra1 = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r1)))
                .andExpect(status().isCreated());
        Integer id1 = (Integer) getResponseFromResult(ra1);
        RebeldeDTO r2 = new RebeldeDTO()
                .setNome("A")
                .setTraidor(false)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Água").setQuantidade(2).setPontos(4))));
        ResultActions ra2 = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r2)))
                .andExpect(status().isCreated());
        Integer id2 = (Integer) getResponseFromResult(ra2);

        TradeDTO r1Trade = new TradeDTO().setId(id1).setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1)));
        TradeDTO r2Trade = new TradeDTO().setId(id2).setItens(Arrays.asList(new ItemDTO().setNome("Água").setQuantidade(2)));

        TradeDTO[] arr = new TradeDTO[]{r1Trade, r2Trade};
        mockMvc.perform(post("/rebelde/trade").contentType("application/json").content(objectMapper.writeValueAsBytes(arr))).andExpect(status().isCreated());

    }

    @Test
    public void givenInputShouldNotTrade() throws Exception {
        RebeldeDTO r1 = new RebeldeDTO()
                .setNome("A")
                .setTraidor(false)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1).setPontos(4))));
        ResultActions ra1 = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r1)))
                .andExpect(status().isCreated());
        Integer id1 = (Integer) getResponseFromResult(ra1);

        RebeldeDTO r2 = new RebeldeDTO()
                .setNome("A")
                .setTraidor(false)
                .setGenero("M")
                .setIdade(33)
                .setLocalizacao(new LocalizacaoDTO().setNome("Galaxy1").setLatitude("1.0").setLongitude("1.0"))
                .setInventario(new InventarioDTO().setItens(Arrays.asList(new ItemDTO().setNome("Água").setQuantidade(1).setPontos(2))));
        ResultActions ra2 = mockMvc.perform(post("/rebelde").contentType("application/json")
                .content(objectMapper.writeValueAsBytes(r2)))
                .andExpect(status().isCreated());
        Integer id2 = (Integer) getResponseFromResult(ra2);

        TradeDTO r1Trade = new TradeDTO().setId(id1).setItens(Arrays.asList(new ItemDTO().setNome("Arma").setQuantidade(1)));
        TradeDTO r2Trade = new TradeDTO().setId(id2).setItens(Arrays.asList(new ItemDTO().setNome("Água").setQuantidade(2)));

        TradeDTO[] arr = new TradeDTO[]{r1Trade, r2Trade};
        mockMvc.perform(post("/rebelde/trade").contentType("application/json").content(objectMapper.writeValueAsBytes(arr))).andExpect(status().isBadRequest());

    }

    private Object getResponseFromResult(ResultActions resultActions) throws Exception {
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        AppResponse appResponse = objectMapper.readValue(contentAsString, AppResponse.class);
        return appResponse.getResponse();
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
