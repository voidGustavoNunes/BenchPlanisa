package com.backend.BenchMarks.model;

import java.time.LocalDate;

import com.backend.BenchMarks.model.dto.ComparacaoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "dados_comparacao")
public class Comparacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate data;
    
    // Dados da primeira localidade
    private String identificadorLocalidade1;
    private Integer confirmados1;
    private Integer mortes1;
    private Double taxaLetalidade1;
    private Long populacaoEstimada1;
    private Double casosPor100kHab1;
    
    // Dados da segunda localidade
    private String identificadorLocalidade2;
    private Integer confirmados2;
    private Integer mortes2;
    private Double taxaLetalidade2;
    private Long populacaoEstimada2;
    private Double casosPor100kHab2;
    
    // Dados comparativos calculados
    private Integer diferencaConfirmados;
    private Integer diferencaMortes;
    private Double diferencaTaxaLetalidade;
    private Double diferencaCasosPor100kHab;
    
    @ManyToOne
    @JoinColumn(name = "comparacao_dto_id", nullable = false)
    private ComparacaoDTO comparacao;
}
