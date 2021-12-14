package com.letscode.starwars.controller;


import com.letscode.starwars.dto.RelatorioDTO;
import com.letscode.starwars.dto.response.AppResponse;
import com.letscode.starwars.service.RelatorioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    private static final Logger logger = LoggerFactory.getLogger(RelatorioController.class);

    @Autowired
    RelatorioService relatorioService;

    @GetMapping("")
    public ResponseEntity getReport() {
        logger.info("Executing " + this.getClass().getName() + ".getReport()");
        RelatorioDTO relatorioDTO = relatorioService.getReport();
        AppResponse appResponse = new AppResponse();
        appResponse.setResponse(appResponse);
        return new ResponseEntity(appResponse, HttpStatus.OK);
    }
}
