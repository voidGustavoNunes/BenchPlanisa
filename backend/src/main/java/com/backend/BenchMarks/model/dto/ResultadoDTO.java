package com.backend.BenchMarks.model.dto;

import java.time.LocalDate;

import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.enums.TipoLocalidade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="resultado")
public class ResultadoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private String localidade;

    TipoLocalidade tipoLocalidade;

    private String confirmacoes;

    private Integer populacaoEstimada;

    private Long casosConfirmados100kHab;

    private Integer mortes;

    private Double taxaLetalidade;

    @ManyToOne
    @JoinColumn(name = "benchmark_id")
    private BenchMark benchmark; // Relacionamento com o Benchmark
}
