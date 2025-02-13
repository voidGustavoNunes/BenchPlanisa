package com.backend.BenchMarks.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.backend.BenchMarks.handler.BusinessException;
import com.backend.BenchMarks.model.APIResponse;
import com.backend.BenchMarks.model.Estado;
import com.backend.BenchMarks.model.Localidade;
import com.backend.BenchMarks.model.Municipio;
import com.backend.BenchMarks.model.Resultado;
import com.backend.BenchMarks.model.enums.TipoLocalidade;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResultadoService {
    private final String API_URL;
    private final String API_TOKEN;
    private final RestTemplate restTemplate;

    public ResultadoService(RestTemplate restTemplate, Dotenv dotenv) {
        this.restTemplate = restTemplate;
        this.API_TOKEN = dotenv.get("API_TOKEN");
        this.API_URL = dotenv.get("API_URL");

    }

    public List<Resultado> buscarDadosCovid(Map<String, String> params) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Token " + API_TOKEN);
            
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_URL);
            params.forEach(builder::queryParam);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<APIResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                APIResponse.class
            );
            
            if (response.getBody() != null && response.getBody().getResults() != null) {
                return response.getBody().getResults();
            }
            
            return new ArrayList<>();
            
        } catch (RestClientException e) {
            log.error("Erro ao buscar dados da API: {}", e.getMessage());
            throw new BusinessException("Erro ao buscar dados da API brasil.io", e);
        }
    }

    public List<Resultado> buscarDadosCovidLocalidade(
            Localidade localidade,
            TipoLocalidade tipo,
            LocalDate dataInicial,
            LocalDate dataFinal) {
            
        Map<String, String> params = new HashMap<>();
        
        // Adiciona parâmetros comuns
        params.put("date_start", dataInicial.toString());
        params.put("date_end", dataFinal.toString());
        
        if (tipo == TipoLocalidade.ESTADO) {
            Estado estado = (Estado) localidade;
            params.put("state", estado.getSigla());
            params.put("place_type", "state");
        } else {
            Municipio municipio = (Municipio) localidade;
            params.put("state", municipio.getEstado().getSigla());
            params.put("city", municipio.getNome());
            params.put("place_type", "city");
        }
        
        return buscarDadosCovid(params);
    }

    public List<Resultado> buscarDadosMaisRecentes(Localidade localidade, TipoLocalidade tipo) {
        Map<String, String> params = new HashMap<>();
        params.put("is_last", "True");
        
        if (tipo == TipoLocalidade.ESTADO) {
            Estado estado = (Estado) localidade;
            params.put("state", estado.getSigla());
            params.put("place_type", "state");
        } else {
            Municipio municipio = (Municipio) localidade;
            params.put("state", municipio.getEstado().getSigla());
            params.put("city", municipio.getNome());
            params.put("place_type", "city");
        }
        
        return buscarDadosCovid(params);
    }

    public List<Resultado> buscarDadosMaisRecentesEstados() {
        Map<String, String> params = new HashMap<>();
        params.put("is_last", "True");
        params.put("place_type", "state");
        
        return buscarDadosCovid(params);
    }



}
