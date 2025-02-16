package com.backend.BenchMarks.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ComparacaoResponseDTO {
    private Long id;
    private BenchMarkDTO benchmark;
    private List<DadosComparacaoDTO> dadosComparacao;
    
}
