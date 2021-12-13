package com.letscode.starwars.repository;

import com.letscode.starwars.entity.Inventario;
import com.letscode.starwars.entity.Item;
import com.letscode.starwars.entity.Localizacao;
import com.letscode.starwars.entity.Rebelde;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RebeldeRepositoryTest {

    @Autowired
    private RebeldeRepository rebeldeRepository;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Test
    public void givenInputShouldCreateRebelde() {
        // Create
        Rebelde rebelde = helper();
        rebeldeRepository.save(rebelde);

        // Check
        Optional<Rebelde> check = rebeldeRepository.findById(rebelde.getId());
        assertEquals(rebelde.getId(), check.get().getId());
    }

    @Test
    public void givenInputShouldUpateRebelde() {
        // Create
        Rebelde rebelde = new Rebelde()
                .setGenero("M")
                .setNome("Anupam")
                .setIdade(33)
                .setTraidor(false);
        rebeldeRepository.save(rebelde);

        // Update Rebelde's nome
        rebelde.setNome("Darth");
        rebeldeRepository.save(rebelde);

        // Check
        Optional<Rebelde> check = rebeldeRepository.findById(rebelde.getId());
        assertEquals("Darth", check.get().getNome());
    }

    @Test
    public void givenInputShouldUpdateReporters() {
        Rebelde rebelde = helper();
        rebeldeRepository.save(rebelde);

        rebeldeRepository.updateReporters(rebelde.getId(), "1:");
        Optional<Rebelde> temp = rebeldeRepository.findById(rebelde.getId());
        assertEquals("1:", temp.get().getReporters());
    }


    private Rebelde helper() {
        Item arma = new Item().setNome("Arma").setQuantidade(1).setPontos(4);
        Inventario inventario = new Inventario().setItens(Arrays.asList(arma));
        Localizacao localizacao = new Localizacao().setLongitude("1.1").setLatitude("2.2").setNome("Milky Way");
        Rebelde rebelde = new Rebelde()
                .setGenero("M")
                .setNome("Anupam")
                .setIdade(33)
                .setTraidor(false)
                .setLocalizacao(localizacao).setInventario(inventario);
        return rebelde;
    }
}
