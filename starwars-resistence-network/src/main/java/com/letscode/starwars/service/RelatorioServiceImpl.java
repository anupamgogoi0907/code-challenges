package com.letscode.starwars.service;

import com.letscode.starwars.dto.RelatorioDTO;
import com.letscode.starwars.entity.Rebelde;
import com.letscode.starwars.exception.StarwarsException;
import com.letscode.starwars.repository.RebeldeRepository;
import com.letscode.starwars.utility.AppUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RelatorioServiceImpl implements RelatorioService {
    private static final Logger logger = LoggerFactory.getLogger(RelatorioServiceImpl.class);
    @Autowired
    RebeldeRepository rebeldeRepository;

    @Override
    public RelatorioDTO getReport() {
        try {
            logger.info("Executing " + this.getClass().getName() + ".getReport()");
            List<Rebelde> listRebelde = (List<Rebelde>) rebeldeRepository.findAll();

            // Calculate %
            double[] arr = AppUtility.calculatePercentage(listRebelde);

            // Calculate Media de Recurso.
            Map<String, Integer> media = AppUtility.calculateStats(listRebelde);
            RelatorioDTO relatorioDTO = new RelatorioDTO();
            relatorioDTO.setPorcentagemRebelde(arr[0]);
            relatorioDTO.setPorcentagemTraidor(arr[1]);
            relatorioDTO.setMedia(media);

            return relatorioDTO;
        } catch (Exception e) {
            throw new StarwarsException.InternalServerException(e.getMessage());
        }

    }


}
