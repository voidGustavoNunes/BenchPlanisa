package com.backend.BenchMarks.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.backend.BenchMarks.handler.BusinessException;
import com.backend.BenchMarks.handler.LocalidadeNaoEncontradaException;
import com.backend.BenchMarks.model.APIResponse;
import com.backend.BenchMarks.util.BuscaStrategy;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class APIBuscaStrategy implements BuscaStrategy {
    private final String API_URL;
    private final String API_TOKEN;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(APIBuscaStrategy.class);

    public APIBuscaStrategy(RestTemplate restTemplate, Dotenv dotenv) {
        this.restTemplate = restTemplate;
        this.API_TOKEN = dotenv.get("API_TOKEN");
        this.API_URL = dotenv.get("API_URL");
    }

    private String convertToAPIEncoding(String text) {
        try {
            logger.debug("Texto original: {}", text);
            
            if (text == null || text.isEmpty()) {
                return text;
            }

            StringBuilder result = new StringBuilder(text);
            
            //Tentativa de manipular os caracteres especiais para pesquisar por exemplo "São Paulo"ou "São José" (que não deu certo por que a API não aceita nem o nome normal nem encodificado),
            //mesmo os dados .csv estando encodificados

            //futura implementacao: popular banco com municipios e estados
            Map<String, String> charMap = new HashMap<>();
            charMap.put("á", "Ã¡");
            charMap.put("à", "Ã ");
            charMap.put("ã", "Ã£");
            charMap.put("â", "Ã¢");
            charMap.put("é", "Ã©");
            charMap.put("ê", "Ãª");
            charMap.put("í", "Ã­");
            charMap.put("ó", "Ã³");
            charMap.put("ô", "Ã´");
            charMap.put("õ", "Ãµ");
            charMap.put("ú", "Ãº");
            charMap.put("ç", "Ã§");
            charMap.put("Á", "Ã");
            charMap.put("À", "Ã€");
            charMap.put("Ã", "Ã");
            charMap.put("Â", "Ã‚");
            charMap.put("É", "Ã‰");
            charMap.put("Ê", "ÃŠ");
            charMap.put("Í", "Ã");
            charMap.put("Ó", "Ã");
            charMap.put("Ô", "Ã");
            charMap.put("Õ", "Ã•");
            charMap.put("Ú", "Ãš");
            charMap.put("Ç", "Ã‡");

            for (int i = 0; i < result.length(); i++) {
                String currentChar = String.valueOf(result.charAt(i));
                if (charMap.containsKey(currentChar)) {
                    String replacement = charMap.get(currentChar);
                    logger.debug("Substituindo '{}' por '{}' na posição {}", 
                               currentChar, replacement, i);
                    result.replace(i, i + 1, replacement);
                    i += replacement.length() - 1;
                }
            }

            String finalResult = result.toString();
            return finalResult;

        } catch (Exception e) {
            throw new BusinessException("Erro ao converter encoding do texto", e);
        }
    }

    @Override
    public List buscarDados(Map<String, String> params) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Token " + API_TOKEN);
            
            Map<String, String> encodedParams = new HashMap<>(params);
            
            String cityName = "";
            if (encodedParams.containsKey("city")) {
                cityName = encodedParams.get("city");
                logger.debug("Convertendo cidade: {}", cityName);
                String cityEncoded = convertToAPIEncoding(cityName);
                logger.debug("Cidade convertida: {}", cityEncoded);
                encodedParams.put("city", cityEncoded);
            }

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_URL);
            encodedParams.forEach(builder::queryParam);

            UriComponents uriComponents = builder.build();
            String finalUrl = uriComponents.toUri().toString();
            logger.debug("URL final: {}", finalUrl);
            
            HttpEntity<?> entity = new HttpEntity<>(headers);
            
            ResponseEntity<APIResponse> response = restTemplate.exchange(
                uriComponents.toUri(),
                HttpMethod.GET,
                entity,
                APIResponse.class
            );

            if (response.getBody() != null && response.getBody().getResults() != null) {
                if (response.getBody().getResults().isEmpty()) {
                    throw new LocalidadeNaoEncontradaException("Localidade não encontrada: " + cityName);
                }
                return response.getBody().getResults();
            }
            return new ArrayList<>();
        } catch (RestClientException e) {
            logger.error("Erro na requisição: ", e);
            throw new BusinessException("Erro ao buscar dados da API brasil.io", e);
        }
    }
}