package com.letscode.starwars.repository;

import com.letscode.starwars.entity.Localizacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LocalizacaoRepositoryTest {

    @Autowired
    LocalizacaoRepository localizacaoRepository;


    @Test
    public void givenLocalizacaoShouldBeCreated() {
        // Save
        Localizacao localizacao = new Localizacao().setLatitude("89.00").setLongitude("99.90").setNome("Racoon");
        localizacaoRepository.save(localizacao);

        // Check.
        Optional<Localizacao> temp = localizacaoRepository.findById(localizacao.getId());
        assertEquals(localizacao.getId(), temp.get().getId());
    }

    @Test
    public void givenLocalizacaoShouldBeUpdated() {
        // Save
        Localizacao localizacao = new Localizacao().setLatitude("89.00").setLongitude("99.90").setNome("Racoon");
        localizacaoRepository.save(localizacao);

        // Update
        localizacao.setNome("Milky Way");
        localizacaoRepository.save(localizacao);

        // Check.
        Optional<Localizacao> temp = localizacaoRepository.findById(localizacao.getId());
        assertEquals("Milky Way", temp.get().getNome());
    }

    @Test
    public void givenLocalizacaoShouldBeDeleted() {
        // Save
        Localizacao localizacao = new Localizacao().setLatitude("89.00").setLongitude("99.90").setNome("Racoon");
        localizacaoRepository.save(localizacao);

        // Delete
        int id = localizacao.getId();
        localizacaoRepository.delete(localizacao);

        // Check.
        Optional<Localizacao> temp = localizacaoRepository.findById(id);
        assertEquals(false, temp.isPresent());
    }
}
