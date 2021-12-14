package com.letscode.starwars.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class TradeDTO {
    @NotNull
    private Integer id;

    @NotNull
    private List<ItemDTO> itens;
}
