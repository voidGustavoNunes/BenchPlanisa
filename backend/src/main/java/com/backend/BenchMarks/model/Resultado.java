package com.backend.BenchMarks.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Resultado {
    
    private LocalDate date; // Data do registro

    private String state; // Estado (UF)

    private String city; // Município

    private String place_type; // Tipo de local (city ou state)

    private Integer confirmed; // Número de casos confirmados

    private Integer deaths; // Número de mortes

    private Boolean is_last; // É a última atualização?

    private Long estimated_population; // População estimada

    private String city_ibge_code; // Código IBGE do município

    private Double confirmed_per_100k_inhabitants; // Confirmados por 100k habitantes
    
    private Double death_rate; // Taxa de mortalidade (mortes/confirmados)

    public Resultado(LocalDate date, String state, String city, int confirmed, int deaths, long estimatedPopulation) {
        this.date = date;
        this.state = state;
        this.city = city;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.estimated_population = estimatedPopulation;
    }

    public Resultado(){
        
    }
    
}
