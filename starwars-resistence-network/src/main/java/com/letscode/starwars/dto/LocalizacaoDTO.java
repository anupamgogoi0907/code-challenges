package com.letscode.starwars.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LocalizacaoDTO {
    private String latitude;
    private String longitude;
    private String nome;
}
