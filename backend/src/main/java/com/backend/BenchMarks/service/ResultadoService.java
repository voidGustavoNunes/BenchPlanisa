package com.backend.BenchMarks.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class ResultadoService {
    private final String API_URL = "https://api.brasil.io/v1/dataset/covid19/caso/data";
    private final String API_TOKEN;
    private final RestTemplate restTemplate;

    public ResultadoService(RestTemplate restTemplate, Dotenv dotenv) {
        this.restTemplate = restTemplate;
        this.API_TOKEN = dotenv.get("API_TOKEN");
    }

    public String getCovidData(String estado) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + API_TOKEN);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        String url = API_URL + "?state=" + estado;
        
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }


    //carregar os municipios por estado
    public String getMunicipiosByEstado(String estado){
        String estados = null;
        return estados;
    }

    //carregar todos os estados cadastrados
    public String getEstados(){
        String estado = null;
        return estado;
    }

}
