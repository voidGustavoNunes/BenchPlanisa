package com.backend.BenchMarks.model.dto;

import java.time.LocalDate;

import com.backend.BenchMarks.model.enums.TipoLocalidade;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BenchMarkDTO {
    private Long id;
    private String localidade1;
    private String localidade2;
    private String estadoLocalidade1;
    private String estadoLocalidade2;
    private TipoLocalidade tipoLocalidade;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicial;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFinal;
    
}
