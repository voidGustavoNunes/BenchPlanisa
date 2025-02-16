package com.backend.BenchMarks.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.Comparacao;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @JsonBackReference
    @JoinColumn(name = "benchmark_id", unique=true)
    private BenchMark benchmark;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comparacao", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comparacao> dadosComparacao;


    public void addDadosComparacao(Comparacao dados) {
        if (dadosComparacao == null) {
            dadosComparacao = new ArrayList<>();
        }
        dadosComparacao.add(dados);
        dados.setComparacao(this);
    }

    public void setDadosComparacao(List<Comparacao> dados) {
        this.dadosComparacao = dados;
        if (dados != null) {
            dados.forEach(dado -> dado.setComparacao(this));
        }
    }
}
