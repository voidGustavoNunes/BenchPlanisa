package com.backend.BenchMarks.util;

import java.util.List;
import java.util.Map;

import com.backend.BenchMarks.model.Resultado;

public interface BuscaStrategy {
    List<Resultado> buscarDados(Map<String, String> params);
}
