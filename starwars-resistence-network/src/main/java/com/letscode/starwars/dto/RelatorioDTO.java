package com.letscode.starwars.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class RelatorioDTO {
    private double porcentagemTraidor;
    private double porcentagemRebelde;
    private Map<String, Integer> media;
}
