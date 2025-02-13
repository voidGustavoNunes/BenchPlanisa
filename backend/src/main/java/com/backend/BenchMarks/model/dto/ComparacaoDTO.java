package com.backend.BenchMarks.model.dto;

import java.util.List;

import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.Comparacao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="comparacao")
public class ComparacaoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "benchmark_id")
    private BenchMark benchmark;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comparacao")
    private List<Comparacao> dadosComparacao;
}
