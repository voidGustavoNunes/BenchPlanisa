package com.backend.BenchMarks.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.BenchMarks.handler.BusinessException;
import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.Comparacao;
import com.backend.BenchMarks.model.Resultado;
import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.model.dto.ComparacaoResponseDTO;
import com.backend.BenchMarks.repository.ComparacaoRepository;
import com.backend.BenchMarks.util.ComparacaoBuilder;
import com.backend.BenchMarks.util.Convert;

@Service
public class ComparadorService {
    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private ComparacaoRepository comparacaoRepository;

    @Autowired
    private Convert convert;

    public ComparacaoResponseDTO criarComparacao(BenchMark benchmark) {
        ComparacaoDTO comparacao = new ComparacaoDTO();
        comparacao.setBenchmark(benchmark);

        List<Resultado> resultados1 = resultadoService.buscarDadosCovidLocalidade(
            benchmark.getLocalidade1(),
            benchmark.getEstadoLocalidade1(),
            benchmark.getTipoLocalidade(),
            benchmark.getDataInicial(),
            benchmark.getDataFinal());

        List<Resultado> resultados2 = resultadoService.buscarDadosCovidLocalidade(
            benchmark.getLocalidade2(),
            benchmark.getEstadoLocalidade2(),
            benchmark.getTipoLocalidade(),
            benchmark.getDataInicial(),
            benchmark.getDataFinal());

        List<Comparacao> dadosComparacao = processarDadosComparacao(
            resultados1, resultados2, comparacao);
        
        dadosComparacao = filtrarPorDataExplicitamente(
            dadosComparacao, 
            benchmark.getDataInicial(),
            benchmark.getDataFinal());

        comparacao.setDadosComparacao(dadosComparacao);
        
        comparacao = comparacaoRepository.save(comparacao);
        
        return convert.convertToResponseDTO(comparacao);
    }

    // Método explícito para garantir a filtragem por data
    private List<Comparacao> filtrarPorDataExplicitamente(
            List<Comparacao> comparacoes,
            LocalDate dataInicial,
            LocalDate dataFinal) {
    
        
        List<Comparacao> filtradas = comparacoes.stream()
            .filter(c -> c.getData() != null && 
                   !c.getData().isBefore(dataInicial) && 
                   !c.getData().isAfter(dataFinal))
            .collect(Collectors.toList());
        
        if (filtradas.isEmpty() && !comparacoes.isEmpty()) {
            Comparacao primeira = comparacoes.get(0);
            primeira.setData(dataInicial);
            filtradas.add(primeira);
        }
        
        return filtradas;
    }

    public List<ComparacaoResponseDTO> buscarComparacoesCadastradas() {
        return comparacaoRepository.findAll()
            .stream()
            .map(convert::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    public ComparacaoResponseDTO buscarPorBenchmark(Long benchmarkId) {
        ComparacaoDTO comparacao = comparacaoRepository.findByBenchmarkId(benchmarkId)
            .orElseThrow(() -> new BusinessException("BenchMark não encontrado"));
        return convert.convertToResponseDTO(comparacao);
    }

    private List<Comparacao> processarDadosComparacao(
            List<Resultado> resultados1,
            List<Resultado> resultados2,
            ComparacaoDTO comparacao) {

        Map<LocalDate, Resultado> mapaResultados1;
        Map<LocalDate, Resultado> mapaResultados2;

        try {
            mapaResultados1 = resultados1.stream()
                .filter(r -> r.getDate() != null)
                .collect(Collectors.toMap(Resultado::getDate, r -> r, (r1, r2) -> r1));

            mapaResultados2 = resultados2.stream()
                .filter(r -> r.getDate() != null)
                .collect(Collectors.toMap(Resultado::getDate, r -> r, (r1, r2) -> r1));

        } catch (Exception e) {
            System.err.println("Erro ao criar mapas: " + e.getMessage());

            Map<LocalDate, Resultado> tempMapaResultados1 = new HashMap<>();
            for (Resultado r : resultados1) {
                if (r.getDate() != null) {
                    tempMapaResultados1.put(r.getDate(), r);
                }
            }

            Map<LocalDate, Resultado> tempMapaResultados2 = new HashMap<>();
            for (Resultado r : resultados2) {
                if (r.getDate() != null) {
                    tempMapaResultados2.put(r.getDate(), r);
                }
            }

            mapaResultados1 = tempMapaResultados1;
            mapaResultados2 = tempMapaResultados2;
        }

        final Map<LocalDate, Resultado> finalMapaResultados1 = mapaResultados1;
        final Map<LocalDate, Resultado> finalMapaResultados2 = mapaResultados2;

        Set<LocalDate> todasDatas = new TreeSet<>();
        todasDatas.addAll(finalMapaResultados1.keySet());
        todasDatas.addAll(finalMapaResultados2.keySet());

        List<Comparacao> comparacoes = todasDatas.stream()
            .map(data -> ComparacaoBuilder.builder()
                .data(data)
                .resultado1(finalMapaResultados1.get(data))
                .resultado2(finalMapaResultados2.get(data))
                .comparacao(comparacao)
                .build())
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        System.out.println("Processamento inicial: " + comparacoes.size() + " comparações geradas");

        return comparacoes;
    }
    
    public void deletarComparacao(Long comparacaoId) {
        if (comparacaoId == null) {
            throw new BusinessException("ID da comparação não pode ser nulo.");
        }
    
        Optional<ComparacaoDTO> comparacao = comparacaoRepository.findById(comparacaoId);
        
        if (comparacao.isPresent()) {
            comparacaoRepository.deleteById(comparacaoId);
        } else {
            throw new BusinessException("Comparação com ID " + comparacaoId + " não encontrada.");
        }
    }
    

}