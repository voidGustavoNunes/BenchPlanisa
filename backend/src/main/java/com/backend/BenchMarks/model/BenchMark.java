package com.backend.BenchMarks.model;

import java.time.LocalDate;

import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.model.enums.TipoLocalidade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name="benchmark")
public class BenchMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo nome não pode ser vazio.")
    @Column(unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "localidade1_id")
    @NotNull(message = "O campo localidade 1 não pode ser vazio.")
    private Localidade localidade1;

    @ManyToOne
    @JoinColumn(name = "localidade2_id")
    @NotNull(message = "O campo localidade 2 não pode ser vazio.")
    private Localidade localidade2;

    @NotNull(message = "O campo data inicial não pode ser vazio.")
    private LocalDate dataInicial;
    
    @NotNull(message = "O campo data final não pode ser vazio.")
    private LocalDate dataFinal;

    @NotNull(message = "É obrigatório identificar qual o tipo de localidade.")
    @Enumerated(EnumType.STRING)
    TipoLocalidade tipoLocalidade;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "benchmark")
    private ComparacaoDTO comparacao;
}

