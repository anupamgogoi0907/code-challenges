package com.letscode.starwars.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RebeldeDTO {
    private Integer id;
    private String nome;
    private Integer idade;
    private String genero;
    private boolean traidor;
    private LocalizacaoDTO localizacao;
    private InventarioDTO inventario;
}
