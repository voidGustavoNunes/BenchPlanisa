package com.backend.BenchMarks.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.backend.BenchMarks.service.LocalidadeService;


import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/localidade")
public class LocalidadeController {

    // private final EstadoRepository estadoRepository;
    // private final MunicipioRepository municipioRepository;

    @Autowired 
    private LocalidadeService localidadeService;

    // public LocalidadeController(EstadoRepository estadoRepository, MunicipioRepository municipioRepository) {
    //     this.estadoRepository = estadoRepository;
    //     this.municipioRepository = municipioRepository;
    // }

    @Operation(summary = "Popula os estados e os municípios", description = "Salva os estados e os municípios no banco de dados com os dados da API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/popular-banco")
    public void popularEstadosEMunicipios(){
        localidadeService.carregarEstadosEMunicipios();
    }
    

    // @Operation(summary = "Popula os estados e os municípios", description = "Salva os estados e os municípios no banco de dados com os dados da API")
    // @GetMapping("/estados")
    // public List<Estado> getEstados() {
    //     List<Estado> estados = estadoRepository.findAll();
    //     if (estados.isEmpty()) {
    //         throw new RegistroNotFoundException("Nenhum estado encontrado no banco de dados.");
    //     }
    //     return estados;
    // }


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
