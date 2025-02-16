package com.backend.BenchMarks.model;

import java.time.LocalDate;

import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.model.enums.TipoLocalidade;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String nome;

    private String localidade1;

    private String localidade2;

    @NotNull(message = "O campo estadoLocalidade2 não pode ser vazio.")
    String estadoLocalidade1;

    @NotNull(message = "O campo estadoLocalidade2 não pode ser vazio.")
    String estadoLocalidade2;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "O campo data inicial não pode ser vazio.")
    private LocalDate dataInicial;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "O campo data final não pode ser vazio.")
    private LocalDate dataFinal;

    @NotNull(message = "É obrigatório identificar qual o tipo de localidade.")
    @Enumerated(EnumType.STRING)
    TipoLocalidade tipoLocalidade;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "benchmark", orphanRemoval = true)
    private ComparacaoDTO comparacao;
}

