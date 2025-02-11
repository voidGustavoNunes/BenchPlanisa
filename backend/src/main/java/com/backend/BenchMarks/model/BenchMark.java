package com.backend.BenchMarks.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class BenchMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // Nome opcional para identificar o benchmark

    private String pais1; // País/Região 1

    private String pais2; // País/Região 2

    private LocalDate dataInicial; // Data inicial do período
    
    private LocalDate dataFinal; // Data final do período

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "benchmark")
    private List<Resultado> resultados; // Lista de resultados associados
}
