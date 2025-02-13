package com.backend.BenchMarks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.BenchMarks.handler.RegistroNotFoundException;
import com.backend.BenchMarks.model.Estado;
import com.backend.BenchMarks.model.Municipio;
import com.backend.BenchMarks.service.LocalidadeService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/localidade")
public class LocalidadeController {

    @Autowired 
    private LocalidadeService localidadeService;


    @Operation(summary = "Popula os estados e os municípios", description = "Salva os estados e os municípios no banco de dados com os dados da API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/popular-banco")
    public void popularEstadosEMunicipios(){
        localidadeService.carregarEstadosEMunicipios();
    }

    @Operation(summary = "Retorna todos os estados", description = "Retorna todos os estados cadastrados no banco de dados")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/estados")
    public List<Estado> getEstados() {
        List<Estado> estados = localidadeService.getEstadosJaCadastrados();
        if (estados.isEmpty()) {
            throw new RegistroNotFoundException("Nenhum estado encontrado no banco de dados.");
        }
        return estados;
    }

    @Operation(summary = "Retorna todos os municipios", description = "Retorna todos os municipios cadastrados no banco de dados")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/municipios")
    public List<Municipio> getMunicipios() {
        List<Municipio> municipios = localidadeService.getMunicipiosJaCadastrados();
        if (municipios.isEmpty()) {
            throw new RegistroNotFoundException("Nenhum municipio encontrado no banco de dados.");
        }
        return municipios;
    }



    // //verificar se vou usar
    // @GetMapping("/municipios/{sigla}")
    // public List<Municipio> getMunicipios(@PathVariable String sigla) {
    //     Estado estado = estadoRepository.findBySigla(sigla)
    //             .orElseThrow(() -> new RegistroNotFoundException("Estado com sigla '" + sigla + "' não encontrado."));

    //     List<Municipio> municipios = municipioRepository.findByEstado_Sigla(sigla);
    //     if (municipios.isEmpty()) {
    //         throw new RegistroNotFoundException("Nenhum município encontrado para o estado: " + sigla);
    //     }
    //     return municipios;
    // }
}
