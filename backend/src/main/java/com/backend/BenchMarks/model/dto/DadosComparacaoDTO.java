package com.backend.BenchMarks.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DadosComparacaoDTO {
    private Long id;
    private LocalDate data;
    private String identificadorLocalidade1;
    private Integer confirmados1;
    private Integer mortes1;
    private Double taxaLetalidade1;
    private Long populacaoEstimada1;
    private Double casosPor100kHab1;
    
    private String identificadorLocalidade2;
    private Integer confirmados2;
    private Integer mortes2;
    private Double taxaLetalidade2;
    private Long populacaoEstimada2;
    private Double casosPor100kHab2;
    
    private Integer diferencaConfirmados;
    private Integer diferencaMortes;
    private Double diferencaTaxaLetalidade;
    private Double diferencaCasosPor100kHab;
    
}
