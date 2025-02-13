package com.backend.BenchMarks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name="municipio")
public class Municipio extends Localidade{

    @NotNull(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 1000, message = "O campo nome deve ter entre 3 e 1000 caracteres")
    @Column(unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    
}
