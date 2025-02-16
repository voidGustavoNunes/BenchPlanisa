package com.backend.BenchMarks.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.Comparacao;
import com.backend.BenchMarks.model.dto.BenchMarkDTO;
import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.model.dto.ComparacaoResponseDTO;
import com.backend.BenchMarks.model.dto.DadosComparacaoDTO;

@Component
public class Convert {
    public ComparacaoResponseDTO convertToResponseDTO(ComparacaoDTO comparacao) {
        if (comparacao == null) {
            return null;
        }
        
        ComparacaoResponseDTO responseDTO = new ComparacaoResponseDTO();
        responseDTO.setId(comparacao.getId());
        responseDTO.setBenchmark(convertToBenchMarkDTO(comparacao.getBenchmark()));
        responseDTO.setDadosComparacao(convertToDadosComparacaoDTO(comparacao.getDadosComparacao()));
        return responseDTO;
    }

    public BenchMarkDTO convertToBenchMarkDTO(BenchMark benchmark) {
        if (benchmark == null) {
            return null;
        }
        
        BenchMarkDTO dto = new BenchMarkDTO();
        dto.setId(benchmark.getId());
        dto.setLocalidade1(benchmark.getLocalidade1());
        dto.setLocalidade2(benchmark.getLocalidade2());
        dto.setEstadoLocalidade1(benchmark.getEstadoLocalidade1());
        dto.setEstadoLocalidade2(benchmark.getEstadoLocalidade2());
        dto.setTipoLocalidade(benchmark.getTipoLocalidade());
        dto.setDataInicial(benchmark.getDataInicial());
        dto.setDataFinal(benchmark.getDataFinal());
        return dto;
    }

    public List<DadosComparacaoDTO> convertToDadosComparacaoDTO(List<Comparacao> comparacoes) {
        if (comparacoes == null) {
            return new ArrayList<>();
        }
        
        return comparacoes.stream()
            .map(this::convertToDadosComparacaoDTO)
            .collect(Collectors.toList());
    }

    public DadosComparacaoDTO convertToDadosComparacaoDTO(Comparacao comparacao) {
        if (comparacao == null) {
            return null;
        }

        DadosComparacaoDTO dto = new DadosComparacaoDTO();
        dto.setId(comparacao.getId());
        dto.setData(comparacao.getData());
        
        dto.setIdentificadorLocalidade1(comparacao.getIdentificadorLocalidade1());
        dto.setConfirmados1(comparacao.getConfirmados1());
        dto.setMortes1(comparacao.getMortes1());
        dto.setTaxaLetalidade1(comparacao.getTaxaLetalidade1());
        dto.setPopulacaoEstimada1(comparacao.getPopulacaoEstimada1());
        dto.setCasosPor100kHab1(comparacao.getCasosPor100kHab1());

        dto.setIdentificadorLocalidade2(comparacao.getIdentificadorLocalidade2());
        dto.setConfirmados2(comparacao.getConfirmados2());
        dto.setMortes2(comparacao.getMortes2());
        dto.setTaxaLetalidade2(comparacao.getTaxaLetalidade2());
        dto.setPopulacaoEstimada2(comparacao.getPopulacaoEstimada2());
        dto.setCasosPor100kHab2(comparacao.getCasosPor100kHab2());

        dto.setDiferencaConfirmados(comparacao.getDiferencaConfirmados());
        dto.setDiferencaMortes(comparacao.getDiferencaMortes());
        dto.setDiferencaTaxaLetalidade(comparacao.getDiferencaTaxaLetalidade());
        dto.setDiferencaCasosPor100kHab(comparacao.getDiferencaCasosPor100kHab());


        return dto;
    }

    public ComparacaoDTO convertToComparacaoDTO(ComparacaoResponseDTO responseDTO) {
        if (responseDTO == null) {
            return null;
        }

        ComparacaoDTO dto = new ComparacaoDTO();
        dto.setId(responseDTO.getId());
        dto.setDadosComparacao(convertToComparacaoList(responseDTO.getDadosComparacao()));
        return dto;
    }

    private List<Comparacao> convertToComparacaoList(List<DadosComparacaoDTO> dadosComparacao) {
        if (dadosComparacao == null) {
            return new ArrayList<>();
        }

        return dadosComparacao.stream()
            .map(this::convertToComparacao)
            .collect(Collectors.toList());
    }

    private Comparacao convertToComparacao(DadosComparacaoDTO dto) {
        if (dto == null) {
            return null;
        }

        Comparacao comparacao = new Comparacao();
        comparacao.setId(dto.getId());
        comparacao.setData(dto.getData());
        
        comparacao.setIdentificadorLocalidade1(dto.getIdentificadorLocalidade1());
        comparacao.setConfirmados1(dto.getConfirmados1());
        comparacao.setMortes1(dto.getMortes1());
        comparacao.setTaxaLetalidade1(dto.getTaxaLetalidade1());
        comparacao.setPopulacaoEstimada1(dto.getPopulacaoEstimada1());
        comparacao.setCasosPor100kHab1(dto.getCasosPor100kHab1());

        comparacao.setIdentificadorLocalidade2(dto.getIdentificadorLocalidade2());
        comparacao.setConfirmados2(dto.getConfirmados2());
        comparacao.setMortes2(dto.getMortes2());
        comparacao.setTaxaLetalidade2(dto.getTaxaLetalidade2());
        comparacao.setPopulacaoEstimada2(dto.getPopulacaoEstimada2());
        comparacao.setCasosPor100kHab2(dto.getCasosPor100kHab2());

        comparacao.setDiferencaConfirmados(dto.getDiferencaConfirmados());
        comparacao.setDiferencaMortes(dto.getDiferencaMortes());
        comparacao.setDiferencaTaxaLetalidade(dto.getDiferencaTaxaLetalidade());
        comparacao.setDiferencaCasosPor100kHab(dto.getDiferencaCasosPor100kHab());

        return comparacao;
    }

    public List<ComparacaoDTO> convertListToComparacaoDTO(List<ComparacaoResponseDTO> responseList) {
        if (responseList == null) {
            return new ArrayList<>();
        }

        return responseList.stream()
            .map(this::convertToComparacaoDTO)
            .collect(Collectors.toList());
    }
}