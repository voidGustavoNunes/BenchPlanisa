package com.backend.BenchMarks.util;

import java.time.LocalDate;
import java.util.Optional;

import com.backend.BenchMarks.model.Comparacao;
import com.backend.BenchMarks.model.Resultado;
import com.backend.BenchMarks.model.dto.ComparacaoDTO;


public class ComparacaoBuilder {
    private LocalDate data;
    private Resultado resultado1;
    private Resultado resultado2;
    private ComparacaoDTO comparacao;

    private ComparacaoBuilder() {}

    public static ComparacaoBuilder builder() {
        return new ComparacaoBuilder();
    }

    public ComparacaoBuilder data(LocalDate data) {
        this.data = data;
        return this;
    }

    public ComparacaoBuilder resultado1(Resultado resultado1) {
        this.resultado1 = resultado1;
        return this;
    }

    public ComparacaoBuilder resultado2(Resultado resultado2) {
        this.resultado2 = resultado2;
        return this;
    }

    public ComparacaoBuilder comparacao(ComparacaoDTO comparacao) {
        this.comparacao = comparacao;
        return this;
    }

    public Comparacao build() {
        if (resultado1 == null || resultado2 == null) {
            return null;
        }

        Comparacao dados = new Comparacao();
        dados.setData(data);
        dados.setComparacao(comparacao);

        dados.setIdentificadorLocalidade1(resultado1.getCity_ibge_code());
        dados.setConfirmados1(Optional.ofNullable(resultado1.getConfirmed()).orElse(0));
        dados.setMortes1(Optional.ofNullable(resultado1.getDeaths()).orElse(0));
        dados.setTaxaLetalidade1(Optional.ofNullable(resultado1.getDeath_rate()).orElse(0.0));
        dados.setPopulacaoEstimada1(Optional.ofNullable(resultado1.getEstimated_population()).orElse(0L));
        dados.setCasosPor100kHab1(Optional.ofNullable(resultado1.getConfirmed_per_100k_inhabitants()).orElse(0.0));

        dados.setIdentificadorLocalidade2(resultado2.getCity_ibge_code());
        dados.setConfirmados2(Optional.ofNullable(resultado2.getConfirmed()).orElse(0));
        dados.setMortes2(Optional.ofNullable(resultado2.getDeaths()).orElse(0));
        dados.setTaxaLetalidade2(Optional.ofNullable(resultado2.getDeath_rate()).orElse(0.0));
        dados.setPopulacaoEstimada2(Optional.ofNullable(resultado2.getEstimated_population()).orElse(0L));
        dados.setCasosPor100kHab2(Optional.ofNullable(resultado2.getConfirmed_per_100k_inhabitants()).orElse(0.0));

        dados.setDiferencaConfirmados(dados.getConfirmados1() - dados.getConfirmados2());
        dados.setDiferencaMortes(dados.getMortes1() - dados.getMortes2());
        dados.setDiferencaTaxaLetalidade(dados.getTaxaLetalidade1() - dados.getTaxaLetalidade2());
        dados.setDiferencaCasosPor100kHab(dados.getCasosPor100kHab1() - dados.getCasosPor100kHab2());

        return dados;
    }
}