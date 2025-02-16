package com.backend.BenchMarks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.backend.BenchMarks.handler.BusinessException;
import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.Resultado;
import com.backend.BenchMarks.model.enums.TipoLocalidade;
import com.backend.BenchMarks.repository.BenchMarkRepository;
import com.backend.BenchMarks.repository.ComparacaoRepository;
import com.backend.BenchMarks.service.BenchMarkService;
import com.backend.BenchMarks.service.ResultadoService;


@SpringBootTest
@Transactional
public class BenchMarkIntegrationTest {

    @Autowired
    private BenchMarkService benchMarkService;

    @Autowired
    private BenchMarkRepository benchMarkRepository;

    @Autowired
    private ComparacaoRepository comparacaoRepository;

    @MockBean
    private ResultadoService resultadoService;

    private BenchMark benchMarkMock;
    private List<Resultado> resultados1;
    private List<Resultado> resultados2;

    @BeforeEach
    public void setup() {
        comparacaoRepository.deleteAll();
        benchMarkRepository.deleteAll();

        benchMarkMock = new BenchMark();
        benchMarkMock.setNome("Benchmark Teste");
        benchMarkMock.setLocalidade1("Campinas");
        benchMarkMock.setLocalidade2("São Paulo");
        benchMarkMock.setEstadoLocalidade1("SP");
        benchMarkMock.setEstadoLocalidade2("SP");
        benchMarkMock.setDataInicial(LocalDate.of(2020, 3, 1));
        benchMarkMock.setDataFinal(LocalDate.of(2020, 6, 1));
        benchMarkMock.setTipoLocalidade(TipoLocalidade.MUNICIPIO);

        resultados1 = new ArrayList<>();
        resultados2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            LocalDate data = LocalDate.of(2020, 3, 1).plusDays(i);
            Resultado r1 = new Resultado(data, "SP", "Campinas", 10 * i, i, 1200000L);
            Resultado r2 = new Resultado(data, "ES", "Linhares", 50 * i, 3 * i, 12000000L);
            resultados1.add(r1);
            resultados2.add(r2);
        }

        when(resultadoService.buscarDadosCovidLocalidade(
                eq("Campinas"), eq("SP"), eq(TipoLocalidade.MUNICIPIO), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(resultados1);

        when(resultadoService.buscarDadosCovidLocalidade(
                eq("Linhares"), eq("ES"), eq(TipoLocalidade.MUNICIPIO), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(resultados2);
    }

    @Test
    public void testCriarBenchMarkComComparacao_Success() {
        BenchMark created = benchMarkService.criar(benchMarkMock);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertNotNull(created.getComparacao());

        Optional<BenchMark> found = benchMarkRepository.findById(created.getId());
        assertTrue(found.isPresent());
        assertEquals(created.getId(), found.get().getId());
    }

    @Test
    public void testCriarBenchMarkComMesmoNome_ThrowsException() {
        benchMarkService.criar(benchMarkMock);

        BenchMark second = new BenchMark();
        second.setNome("Benchmark Teste");
        second.setLocalidade1("Manaus");
        second.setLocalidade2("Cariacica");
        second.setEstadoLocalidade1("AM");
        second.setEstadoLocalidade2("ES");
        second.setDataInicial(LocalDate.of(2020, 3, 1));
        second.setDataFinal(LocalDate.of(2020, 6, 1));
        second.setTipoLocalidade(TipoLocalidade.MUNICIPIO);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            benchMarkService.criar(second);
        });

        assertTrue(exception.getMessage().contains("Já existe um benchmark cadastrado com esse nome"));
    }

    @Test
    public void testAtualizarBenchMark_Success() {
        BenchMark created = benchMarkService.criar(benchMarkMock);
        created.setNome("Benchmark Atualizado");
        BenchMark updated = benchMarkService.atualizar(created.getId(), created);

        assertNotNull(updated);
        assertEquals("Benchmark Atualizado", updated.getNome());
    }
}

