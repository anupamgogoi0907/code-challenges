package com.letscode.starwars.service;


import com.letscode.starwars.dto.*;
import com.letscode.starwars.entity.Inventario;
import com.letscode.starwars.entity.Item;
import com.letscode.starwars.entity.Localizacao;
import com.letscode.starwars.entity.Rebelde;
import com.letscode.starwars.exception.StarwarsException;
import com.letscode.starwars.repository.RebeldeRepository;
import com.letscode.starwars.utility.AppUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RebeldeServiceImpl implements RebeldeService {
    private static final Logger logger = LoggerFactory.getLogger(RebeldeServiceImpl.class);

    @Autowired
    private RebeldeRepository rebeldeRepository;

    @Autowired
    private ItemService itemService;

    /**
     * Get all Rebeldes.
     *
     * @return
     */
    @Override
    public List<RebeldeDTO> getAllRebeldes() {
        try {
            logger.info("Executing " + this.getClass().getName() + ".getAllRebeldes()");
            List<Rebelde> list = (List<Rebelde>) rebeldeRepository.findAll();
            List<RebeldeDTO> resultList = new ArrayList<>();

            list.forEach(r -> {
                // Create LocalizacaoDTO.
                LocalizacaoDTO localizacaoDTO = new LocalizacaoDTO()
                        .setNome(r.getLocalizacao().getNome())
                        .setLatitude(r.getLocalizacao().getLatitude())
                        .setLongitude(r.getLocalizacao().getLongitude());

                // Create InventarioDTO.
                List<ItemDTO> itemDTOList = new ArrayList<>();
                if (r.getInventario().getItens() != null) {
                    r.getInventario().getItens().forEach(i -> {
                        ItemDTO itemDTO = new ItemDTO()
                                .setNome(i.getNome())
                                .setQuantidade(i.getQuantidade())
                                .setPontos(i.getPontos());
                        itemDTOList.add(itemDTO);
                    });
                }
                InventarioDTO inventarioDTO = new InventarioDTO().setItens(itemDTOList);

                // Create RebeldeDTO
                RebeldeDTO rebeldeDTO = new RebeldeDTO();
                rebeldeDTO.setId(r.getId());
                rebeldeDTO.setNome(r.getNome());
                rebeldeDTO.setIdade(r.getIdade());
                rebeldeDTO.setGenero(r.getGenero());
                rebeldeDTO.setTraidor(r.isTraidor());
                rebeldeDTO.setLocalizacao(localizacaoDTO);
                rebeldeDTO.setInventario(inventarioDTO);
                resultList.add(rebeldeDTO);
            });
            return resultList;
        } catch (Exception e) {
            throw new StarwarsException.EntityNotFoundException(e.getMessage());
        }

    }

    /**
     * Create a Rebelde
     *
     * @param rebeldeDTO
     * @return
     */
    @Override
    public Integer saveRebelde(RebeldeDTO rebeldeDTO) {
        try {
            logger.info("Executing " + this.getClass().getName() + ".saveRebelde()");
            // Create Localizacao
            Localizacao localizacao = new Localizacao()
                    .setNome(rebeldeDTO.getLocalizacao().getNome())
                    .setLatitude(rebeldeDTO.getLocalizacao().getLatitude())
                    .setLongitude(rebeldeDTO.getLocalizacao().getLongitude());

            // Create Inventario
            List<Item> itemList = new ArrayList<>();
            rebeldeDTO.getInventario().getItens().forEach(i -> {
                Item item = new Item()
                        .setNome(i.getNome())
                        .setQuantidade(i.getQuantidade())
                        .setPontos(i.getQuantidade() * AppUtility.getPontosByItem(i.getNome()));
                itemList.add(item);
            });
            Inventario inventario = new Inventario().setItens(itemList);

            // Create Rebelde
            Rebelde rebelde = new Rebelde()
                    .setNome(rebeldeDTO.getNome())
                    .setIdade(rebeldeDTO.getIdade())
                    .setGenero(rebeldeDTO.getGenero())
                    .setTraidor(rebeldeDTO.isTraidor())
                    .setLocalizacao(localizacao)
                    .setInventario(inventario);
            rebeldeRepository.save(rebelde);
            return rebelde.getId();
        } catch (Exception e) {
            throw new StarwarsException.InternalServerException(e.getMessage());
        }

    }

    /**
     * Find Rebelde by its id.
     *
     * @param id
     * @return
     */
    @Override
    public RebeldeDTO findById(Integer id) {
        try {
            logger.info("Executing " + this.getClass().getName() + ".findById()");
            Optional<Rebelde> rebelde = rebeldeRepository.findById(id);
            Rebelde r = rebelde.get();

            // Create LocalizacaoDTO
            LocalizacaoDTO localizacaoDTO = new LocalizacaoDTO()
                    .setNome(r.getLocalizacao().getNome())
                    .setLatitude(r.getLocalizacao().getLatitude())
                    .setLongitude(r.getLocalizacao().getLongitude());
            // Create InventarioDTO
            List<ItemDTO> itemList = new ArrayList<>();
            r.getInventario().getItens().forEach(i -> {
                ItemDTO itemDTO = new ItemDTO()
                        .setNome(i.getNome())
                        .setQuantidade(i.getQuantidade())
                        .setPontos(i.getPontos());
                itemList.add(itemDTO);
            });
            InventarioDTO inventario = new InventarioDTO().setItens(itemList);

            // Create RebeldeDTO
            RebeldeDTO rebeldeDTO = new RebeldeDTO()
                    .setId(r.getId())
                    .setNome(r.getNome())
                    .setGenero(r.getGenero())
                    .setIdade(r.getIdade())
                    .setTraidor(r.isTraidor())
                    .setLocalizacao(localizacaoDTO)
                    .setInventario(inventario);
            return rebeldeDTO;
        } catch (Exception e) {
            throw new StarwarsException.EntityNotFoundException(e.getMessage());
        }

    }

    /**
     * Update local of a Rebelde.
     *
     * @param rebeldeId
     * @param localizacaoDTO
     */
    @Override
    public void updateLocal(Integer rebeldeId, LocalizacaoDTO localizacaoDTO) {
        try {
            logger.info("Executing " + this.getClass().getName() + ".updateLocal()");
            Optional<Rebelde> rebelde = rebeldeRepository.findById(rebeldeId);
            if (rebelde.isPresent()) {
                Rebelde r = rebelde.get();
                r.getLocalizacao().setNome(localizacaoDTO.getNome()).setLatitude(localizacaoDTO.getLatitude()).setLongitude(localizacaoDTO.getLongitude());
                rebeldeRepository.save(r);
            }
        } catch (Exception e) {
            throw new StarwarsException.InternalServerException(e.getMessage());
        }
    }

    /**
     * @param reporterDTO
     * @return
     */
    @Override
    public String updateReporters(ReporterDTO reporterDTO) {
        String res = "";
        try {
            logger.info("Executing " + this.getClass().getName() + ".updateReportCount()");
            Optional<Rebelde> opt_reporter = rebeldeRepository.findById(reporterDTO.getIdReporter());
            Optional<Rebelde> opt_traidor = rebeldeRepository.findById(reporterDTO.getIdTraidor());
            if (opt_reporter.isPresent() && opt_traidor.isPresent()) {
                Rebelde reporter = opt_reporter.get();
                Rebelde traidor = opt_traidor.get();
                String reporters = traidor.getReporters();

                if (traidor.isTraidor()) {
                    res = "O Rebelde já é Traidor. Não se pode reportar novamente.";
                    return res;
                }

                if (reporters == null || reporters.isEmpty()) {
                    reporters = reporter.getId() + "";
                    rebeldeRepository.updateReporters(traidor.getId(), reporters);
                    res = "Rebelde " + traidor.getId() + " foi reportado como Traidor pelo: " + reporter.getId();
                }
                // Check if the same reporter is reporting.
                else {
                    String[] arr = reporters.split(":");
                    if (Arrays.asList(arr).contains(reporter.getId().toString())) {
                        res = reporter.getId() + " já reportou o Traidor: " + traidor.getId();
                    } else {
                        reporters = reporters + ":" + reporter.getId();
                        if (arr.length == 2) {
                            rebeldeRepository.updateReporters(traidor.getId(), reporters);
                            rebeldeRepository.updateTraidor(traidor.getId(), true);
                            res = "Rebelde foi marcado como Traidor";
                        } else if (arr.length < 2) {
                            rebeldeRepository.updateReporters(traidor.getId(), reporters);
                            res = "Rebelde " + traidor.getId() + " foi reportado como Traidor pelo: " + reporter.getId();
                        }
                    }
                }
                return res;
            } else {
                throw new StarwarsException.EntityNotFoundException("Either Reportar or Traidor not found.");
            }
        } catch (Exception e) {
            throw new StarwarsException.InternalServerException(e.getMessage());
        }

    }

    /**
     * Trading
     *
     * @param trade1
     * @param trade2
     * @return
     */
    @Override
    public boolean exchangeInventory(TradeDTO trade1, TradeDTO trade2) {
        logger.info("Executing " + this.getClass().getName() + ".exchangeInventory()");

        try {
            Optional<Rebelde> optTrade1 = rebeldeRepository.findById(trade1.getId());
            Optional<Rebelde> optTrade2 = rebeldeRepository.findById(trade2.getId());

            // Check if rebelde is found in database.
            if (!optTrade1.isPresent() || !optTrade2.isPresent()) {
                return false;
            }

            Rebelde r1 = optTrade1.get();
            Rebelde r2 = optTrade2.get();

            // Check if traidor
            if (r1.isTraidor() || r2.isTraidor()) {
                return false;
            }

            // Get tradable items.
            List<Item> tradableItems_r1 = AppUtility.validateItems(r1.getInventario().getItens(), trade1.getItens());
            List<Item> tradableItems_r2 = AppUtility.validateItems(r2.getInventario().getItens(), trade2.getItens());

            // Check if there is item for trade.
            if (tradableItems_r1.isEmpty() || tradableItems_r2.isEmpty()) {
                return false;
            }

            // Check if pontos are equal
            if (!AppUtility.validatePontos(tradableItems_r1, tradableItems_r2)) {
                return false;
            }

            // Rebelde 1's inventory = Items + Rebelde 2's Trading items
            List<Item> r1AfterAdding = AppUtility.addItems(r1.getInventario().getItens(), tradableItems_r2);
            // Rebelde 1's inventory = Items - Rebelde 1's Trading items
            List<Item> r1Final = AppUtility.removeItems(r1AfterAdding, tradableItems_r1);

            // Rebelde 2's inventory = Items + Rebelde 1's Trading items
            List<Item> r2AfterAdding = AppUtility.addItems(r2.getInventario().getItens(), tradableItems_r1);
            // Rebelde 2's inventory = Items - Rebelde 2's Trading items
            List<Item> r2Final = AppUtility.removeItems(r2AfterAdding, tradableItems_r2);

            itemService.updateItems(r1.getInventario().getId(), r1Final);
            itemService.updateItems(r2.getInventario().getId(), r2Final);
            return true;
        } catch (Exception e) {
            throw new StarwarsException.InternalServerException(e.getMessage());
        }

    }


}
