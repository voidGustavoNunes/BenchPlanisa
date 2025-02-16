package com.backend.BenchMarks.model.dto;

import java.util.List;

import lombok.Builder;

@Builder
public class ComparacaoResponseDTOBuilder {
    private Long id;
    private BenchMarkDTO benchmark;
    private List<DadosComparacaoDTO> dadosComparacao;

    public ComparacaoResponseDTO build() {
        ComparacaoResponseDTO dto = new ComparacaoResponseDTO();
        dto.setId(id);
        dto.setBenchmark(benchmark);
        dto.setDadosComparacao(dadosComparacao);
        return dto;
    }
}