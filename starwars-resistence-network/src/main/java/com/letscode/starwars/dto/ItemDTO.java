package com.letscode.starwars.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ItemDTO {
    private String nome;
    private Integer quantidade;
    private Integer pontos;
}
