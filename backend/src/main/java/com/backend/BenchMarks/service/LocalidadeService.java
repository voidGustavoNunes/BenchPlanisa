package com.backend.BenchMarks.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.backend.BenchMarks.handler.RegistroNotFoundException;
import com.backend.BenchMarks.model.Estado;
import com.backend.BenchMarks.model.Localidade;
import com.backend.BenchMarks.model.Municipio;
import com.backend.BenchMarks.repository.EstadoRepository;
import com.backend.BenchMarks.repository.LocalidadeRepository;
import com.backend.BenchMarks.repository.MunicipioRepository;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class LocalidadeService {
    private final String API_URL;
    private final String API_TOKEN;
    private final RestTemplate restTemplate;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private LocalidadeRepository localidadeRepository;


    public LocalidadeService(RestTemplate restTemplate, Dotenv dotenv) {
        this.restTemplate = restTemplate;
        this.API_TOKEN = dotenv.get("API_TOKEN");
        this.API_URL = dotenv.get("API_URL");

    }


    public void carregarEstadosEMunicipios() { //implementar businessexception
        Set<String> estados = buscarEstadosDaAPI();
        for (String sigla : estados) {
            Optional<Estado> estadoExistente = estadoRepository.findBySigla(sigla);
            Estado estado = estadoExistente.orElseGet(() -> {
                Estado novoEstado = new Estado();
                novoEstado.setSigla(sigla);
                return estadoRepository.save(novoEstado);
            });
    
            buscarEMunicipiosSalvar(estado);
        }
    }


    private Set<String> buscarEstadosDaAPI() { //implementar businessexception
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + API_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);
        JsonObject jsonResponse = JsonParser.parseString(response.getBody()).getAsJsonObject();
        JsonArray results = jsonResponse.getAsJsonArray("results");

        Set<String> estados = new HashSet<>();

        for (int i = 0; i < results.size(); i++) {
            JsonObject item = results.get(i).getAsJsonObject();
            String estadoSigla = item.get("state").getAsString();
            estados.add(estadoSigla);
        }

        return estados;
    }


    private void buscarEMunicipiosSalvar(Estado estado) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + API_TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = API_URL + "?state=" + estado.getSigla();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        JsonObject jsonResponse = JsonParser.parseString(response.getBody()).getAsJsonObject();
        JsonArray results = jsonResponse.getAsJsonArray("results");

        List<Municipio> municipios = results.asList().stream()
                .map(m -> m.getAsJsonObject().get("city").getAsString())
                .distinct()
                .map(nome -> {
                    Municipio municipio = new Municipio();
                    municipio.setNome(nome);
                    municipio.setEstado(estado);
                    return municipio;
                })
                .collect(Collectors.toList());

        municipioRepository.saveAll(municipios); //implementar businessexception
    }

    public List<Estado> getEstadosJaCadastrados(){
        List<Estado> estados = estadoRepository.findAll();
        if (estados.isEmpty()) {
            throw new RegistroNotFoundException("Nenhum estado encontrado no banco de dados.");
        }
        return estados;
    }

    public List<Municipio> getMunicipiosJaCadastrados(){
        List<Municipio> municipios = municipioRepository.findAll();
        if (municipios.isEmpty()) {
            throw new RegistroNotFoundException("Nenhum municipio encontrado no banco de dados.");
        }
        return municipios;
    }

    public Localidade buscarPorId(Long id) {
        return localidadeRepository.findById(id).orElseThrow(() -> new RegistroNotFoundException(id));
    }
}
