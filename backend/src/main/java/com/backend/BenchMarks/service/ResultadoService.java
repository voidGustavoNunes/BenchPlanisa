package com.backend.BenchMarks.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.backend.BenchMarks.model.Resultado;
import com.backend.BenchMarks.model.enums.TipoLocalidade;
import com.backend.BenchMarks.util.BuscaStrategy;

@Service
public class ResultadoService {
    private final BuscaStrategy buscaStrategy;

    public ResultadoService(BuscaStrategy buscaStrategy) {
        this.buscaStrategy = buscaStrategy;
    }

    public List<Resultado> buscarDadosCovidLocalidade(
            String localidade,
            String estado,
            TipoLocalidade tipo,
            LocalDate dataInicial,
            LocalDate dataFinal) {

        Map<String, String> params = new HashMap<>();
        params.put("date_start", dataInicial.toString());
        params.put("date_end", dataFinal.toString());

        if (tipo == TipoLocalidade.MUNICIPIO) {
            params.put("state", estado);
            params.put("city", localidade);
            params.put("place_type", "city");
        }else{
            return List.of();
        }

        return buscaStrategy.buscarDados(params);
    }
}