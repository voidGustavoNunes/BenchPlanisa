package com.backend.BenchMarks.model;

import java.time.LocalDate;
import java.util.List;

import com.backend.BenchMarks.model.dto.ResultadoDTO;
import com.backend.BenchMarks.model.enums.TipoLocalidade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @NotNull(message = "O campo localidade 1 não pode ser vazio.")
    private String localidade1;

    @NotNull(message = "O campo localidade 2 não pode ser vazio.")
    private String localidade2;

    @NotNull(message = "O campo data inicial não pode ser vazio.")
    private LocalDate dataInicial;
    
    @NotNull(message = "O campo data final não pode ser vazio.")
    private LocalDate dataFinal;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "benchmark")
    private List<ResultadoDTO> resultados;

    @NotNull(message = "É obrigatório identificar qual o tipo de localidade.")
    TipoLocalidade tipoLocalidade;
}

