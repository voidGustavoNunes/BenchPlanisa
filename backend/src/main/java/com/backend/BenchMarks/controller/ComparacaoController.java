package com.backend.BenchMarks.controller;

import com.backend.BenchMarks.model.dto.ComparacaoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import com.backend.BenchMarks.service.ComparadorService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Validated
@RestController
@RequestMapping("/api/comparacao")
@Tag(name = "Comparacoes", description = "Operações relacionadas as comparações")
public class ComparacaoController {

    @Autowired
    private ComparadorService comparadorService;
    

    @GetMapping("/all")
    public List<ComparacaoDTO> getAllComparacoes() {
        return comparadorService.buscarComparacoes();
    }

}
