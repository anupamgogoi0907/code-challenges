package com.letscode.starwars.controller;

import com.letscode.starwars.dto.LocalizacaoDTO;
import com.letscode.starwars.dto.RebeldeDTO;
import com.letscode.starwars.dto.ReporterDTO;
import com.letscode.starwars.dto.TradeDTO;
import com.letscode.starwars.dto.response.AppResponse;
import com.letscode.starwars.service.RebeldeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rebelde")
public class RebeldeController {
    private static final Logger logger = LoggerFactory.getLogger(RebeldeController.class);

    @Autowired
    RebeldeService rebeldeService;

    @GetMapping("")
    public ResponseEntity getRebeldes() {
        logger.info("Executing " + this.getClass().getName() + ".getRebeldes()");
        List<RebeldeDTO> rebeldeDTOList = rebeldeService.getAllRebeldes();
        return ResponseEntity.ok(rebeldeDTOList);
    }

    @PostMapping("")
    public ResponseEntity createRebelde(@RequestBody RebeldeDTO rebeldeDTO) {
        logger.info("Executing " + this.getClass().getName() + ".createRebelde()");
        Integer id = rebeldeService.saveRebelde(rebeldeDTO);
        logger.info("Rebelde created: " + id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Integer id) {
        logger.info("Executing " + this.getClass().getName() + ".findById()");
        RebeldeDTO rebeldeDTO = rebeldeService.findById(id);
        return ResponseEntity.ok(rebeldeDTO);
    }

    @PatchMapping("/{id}/reportar/local")
    public ResponseEntity reportarLocal(@PathVariable("id") Integer id, @RequestBody LocalizacaoDTO localizacaoDTO) {
        logger.info("Executing " + this.getClass().getName() + ".findById()");
        rebeldeService.updateLocal(id, localizacaoDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    @PostMapping("/reportar/traidor")
    public ResponseEntity reportarTraidor(@RequestBody ReporterDTO reportarDTO) {
        logger.info("Executing " + this.getClass().getName() + ".reportarTraidor()");
        String res = rebeldeService.updateReportCount(reportarDTO);
        AppResponse appResponse = new AppResponse();
        appResponse.setPayload(res);
        return new ResponseEntity(appResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/trade")
    public ResponseEntity trade(@RequestBody TradeDTO[] tradeDTOS) {
        logger.info("Executing " + this.getClass().getName() + ".trade()");
        boolean flag = rebeldeService.exchangeInventory(tradeDTOS[0], tradeDTOS[1]);
        AppResponse appResponse = new AppResponse();
        if (flag) {
            appResponse.setPayload("Trade successful");
            return new ResponseEntity(appResponse, HttpStatus.CREATED);
        } else {
            appResponse.setPayload("Trade Failed");
            return new ResponseEntity(appResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
