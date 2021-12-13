package com.letscode.starwars.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class InventarioDTO {
    private List<ItemDTO> itens;

}
