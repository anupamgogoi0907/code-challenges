package com.letscode.starwars.service;

import com.letscode.starwars.dto.LocalizacaoDTO;
import com.letscode.starwars.dto.RebeldeDTO;
import com.letscode.starwars.dto.ReporterDTO;
import com.letscode.starwars.dto.TradeDTO;

import java.util.List;

public interface RebeldeService {

    public List<RebeldeDTO> getAllRebeldes();

    public Integer saveRebelde(RebeldeDTO rebeldeDTO);

    public RebeldeDTO findById(Integer id);

    public void updateLocal(Integer rebeldeId, LocalizacaoDTO localizacaoDTO);

    public String updateReporters(ReporterDTO reporterDTO);

    public boolean exchangeInventory(TradeDTO offer, TradeDTO receptor);
}
