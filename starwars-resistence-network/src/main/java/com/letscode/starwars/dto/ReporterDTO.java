package com.letscode.starwars.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ReporterDTO {
    private Integer idReporter;
    private Integer idTraidor;
}
