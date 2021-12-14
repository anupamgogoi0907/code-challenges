package com.letscode.starwars.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class ItemDTO {
    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    private Integer quantidade;

    @NotNull
    private Integer pontos;
}
