package com.letscode.starwars.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class LocalizacaoDTO {

    @NotNull
    @NotEmpty
    private String latitude;

    @NotNull
    @NotEmpty
    private String longitude;

    @NotNull
    @NotEmpty
    private String nome;
}
