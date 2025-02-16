package com.backend.BenchMarks.controller;

import com.backend.BenchMarks.handler.BusinessException;
import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.model.dto.ComparacaoResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import com.backend.BenchMarks.service.ComparadorService;
import com.backend.BenchMarks.util.Convert;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;

@Controller
@Validated
@RestController
@RequestMapping("/api/comparacao")
@Tag(name = "Comparacoes", description = "Operações relacionadas as comparações")
public class ComparacaoController { 

    @Autowired
    private Convert convert;

    @Autowired
    private ComparadorService comparadorService;

    @GetMapping("/all")
    @Operation(summary = "Listar Comparações", description = "Retorna todas as comparações cadastradas")
    public ResponseEntity<List<ComparacaoDTO>> getAllComparacoes() {
        try {
            List<ComparacaoResponseDTO> comparacoes = comparadorService.buscarComparacoesCadastradas();
            return ResponseEntity.ok(convert.convertListToComparacaoDTO(comparacoes));
        } catch (BusinessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/buscar/{benchmarkId}")
    @Operation(summary = "Buscar por Benchmark", description = "Retorna a comparação associada a um benchmark específico")
    public ResponseEntity<ComparacaoDTO> obterComparacaoPorBenchmark(
            @PathVariable @Positive Long benchmarkId) {
        try {
            ComparacaoResponseDTO comparacao = comparadorService.buscarPorBenchmark(benchmarkId);
            return ResponseEntity.ok(convert.convertToComparacaoDTO(comparacao));
        } catch (BusinessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
