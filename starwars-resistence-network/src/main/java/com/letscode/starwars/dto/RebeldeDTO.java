package com.letscode.starwars.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class RebeldeDTO {
    private Integer id;

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    private Integer idade;

    @NotNull
    @NotEmpty
    private String genero;

    private boolean traidor;

    @NotNull
    private LocalizacaoDTO localizacao;

    @NotNull
    private InventarioDTO inventario;
}
