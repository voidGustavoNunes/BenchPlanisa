package com.backend.BenchMarks.model;

import java.time.LocalDate;

import lombok.Data;


@Data
public class Resultado {
    
    private LocalDate date; // Data do registro

    private String state; // Estado (UF)

    private String city; // Município

    private String placeType; // Tipo de local (city ou state)

    private Integer confirmed; // Número de casos confirmados

    private Integer deaths; // Número de mortes

    private Boolean isLast; // É a última atualização?

    private Long estimatedPopulation; // População estimada

    private String cityIbgeCode; // Código IBGE do município

    private Double confirmedPer100kInhabitants; // Confirmados por 100k habitantes
    
    private Double deathRate; // Taxa de mortalidade (mortes/confirmados)
}
