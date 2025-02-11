package com.backend.BenchMarks.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data; // Data do registro

    private String pais; // País/Região

    private Integer casosConfirmados; // Número de casos confirmados

    private Integer mortes; // Número de mortes

    private Double taxaLetalidade; // Taxa de letalidade (mortes / casos confirmados)

    @ManyToOne
    @JoinColumn(name = "benchmark_id")
    private BenchMark benchmark; // Relacionamento com o Benchmark
}
