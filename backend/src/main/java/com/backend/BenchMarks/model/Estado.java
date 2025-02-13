package com.backend.BenchMarks.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="estado")
public class Estado extends Localidade {

    @NotNull(message = "O campo sigla é obrigatório")
    @Size(min = 2, max = 2, message = "O campo sigla deve ter 2 caracteres")
    @Column(unique = true)
    private String sigla;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Municipio> municipios;
    
}
