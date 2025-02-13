package com.backend.BenchMarks.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.Comparacao;
import com.backend.BenchMarks.model.Resultado;
import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.repository.ComparacaoRepository;

@Service
public class ComparadorService {

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private ComparacaoRepository comparacaoRepository;

    protected ComparadorService(){}
    
    public ComparacaoDTO criarComparacao(BenchMark benchmark) {
        ComparacaoDTO comparacao = new ComparacaoDTO();
        comparacao.setBenchmark(benchmark);
        
        // Buscar dados da API para ambas localidades
        List<Resultado> resultados1 = resultadoService.buscarDadosCovidLocalidade(benchmark.getLocalidade1(),
            benchmark.getTipoLocalidade(), benchmark.getDataInicial(), benchmark.getDataFinal());
        
        List<Resultado> resultados2 = resultadoService.buscarDadosCovidLocalidade(benchmark.getLocalidade2(), 
            benchmark.getTipoLocalidade(), benchmark.getDataInicial(), benchmark.getDataFinal());
        
        // Processar e criar dados de comparação
        List<Comparacao> dadosComparacao = processarDadosComparacao(
            resultados1, resultados2, comparacao);
        
        comparacao.setDadosComparacao(dadosComparacao);
        return comparacao;
    }

    private List<Comparacao> processarDadosComparacao(List<Resultado> resultados1,
            List<Resultado> resultados2, ComparacaoDTO comparacao) {
        
        Map<LocalDate, Resultado> mapaResultados1 = resultados1.stream()
            .collect(Collectors.toMap(Resultado::getDate, r -> r));
            
        Map<LocalDate, Resultado> mapaResultados2 = resultados2.stream()
            .collect(Collectors.toMap(Resultado::getDate, r -> r));
            
        Set<LocalDate> todasDatas = new TreeSet<>();
        todasDatas.addAll(mapaResultados1.keySet());
        todasDatas.addAll(mapaResultados2.keySet());
        
        return todasDatas.stream()
            .map(data -> criarDadosComparacao(data,
                mapaResultados1.get(data),
                mapaResultados2.get(data),
                comparacao))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private Comparacao criarDadosComparacao(LocalDate data, Resultado r1,
            Resultado r2, ComparacaoDTO comparacao) {
        if (r1 == null || r2 == null) return null;

        Comparacao dados = new Comparacao();
        dados.setData(data);
        dados.setComparacao(comparacao);

        // Dados da primeira localidade
        dados.setConfirmados1(r1.getConfirmed());
        dados.setMortes1(r1.getDeaths());
        dados.setTaxaLetalidade1(r1.getDeathRate());
        dados.setPopulacaoEstimada1(r1.getEstimatedPopulation());
        dados.setCasosPor100kHab1(r1.getConfirmedPer100kInhabitants());

        // Dados da segunda localidade
        dados.setConfirmados2(r2.getConfirmed());
        dados.setMortes2(r2.getDeaths());
        dados.setTaxaLetalidade2(r2.getDeathRate());
        dados.setPopulacaoEstimada2(r2.getEstimatedPopulation());
        dados.setCasosPor100kHab2(r2.getConfirmedPer100kInhabitants());

        // Calcular diferenças
        dados.setDiferencaConfirmados(r1.getConfirmed() - r2.getConfirmed());
        dados.setDiferencaMortes(r1.getDeaths() - r2.getDeaths());
        dados.setDiferencaTaxaLetalidade(r1.getDeathRate() - r2.getDeathRate());
        dados.setDiferencaCasosPor100kHab(
            r1.getConfirmedPer100kInhabitants() - r2.getConfirmedPer100kInhabitants());

        return dados;
    }

    public List<ComparacaoDTO> buscarComparacoes() {
        return comparacaoRepository.findAll();
    }

}
