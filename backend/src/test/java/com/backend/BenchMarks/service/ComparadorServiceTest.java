package com.backend.BenchMarks.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.backend.BenchMarks.handler.BusinessException;
import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.Resultado;
import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.model.dto.ComparacaoResponseDTO;
import com.backend.BenchMarks.model.enums.TipoLocalidade;
import com.backend.BenchMarks.repository.ComparacaoRepository;
import com.backend.BenchMarks.util.Convert;

@ExtendWith(MockitoExtension.class)
public class ComparadorServiceTest {

    @Mock
    private ResultadoService resultadoService;

    @Mock
    private ComparacaoRepository comparacaoRepository;

    @Mock
    private Convert convert;

    @InjectMocks
    private ComparadorService comparadorService;

    private BenchMark benchMarkMock;
    private List<Resultado> resultados1;
    private List<Resultado> resultados2;
    private ComparacaoDTO comparacaoDTO;
    private ComparacaoResponseDTO comparacaoResponseDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        benchMarkMock = new BenchMark();
        benchMarkMock.setId(1L);
        benchMarkMock.setNome("Benchmark teste");
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
            
            Resultado r1 = new Resultado();
            r1.setDate(data);
            r1.setCity("Campinas");
            r1.setState("SP");
            r1.setConfirmed(i * 10);
            r1.setDeaths(i);
            r1.setEstimated_population(1200000L);
            
            Resultado r2 = new Resultado();
            r2.setDate(data);
            r2.setCity("Linhares");
            r2.setState("ES");
            r2.setConfirmed(i * 50);
            r2.setDeaths(i * 3);
            r2.setEstimated_population(12000000L);
            
            resultados1.add(r1);
            resultados2.add(r2);
        }
        
        comparacaoDTO = new ComparacaoDTO();
        comparacaoDTO.setId(1L);
        comparacaoDTO.setBenchmark(benchMarkMock);
        
        comparacaoResponseDTO = new ComparacaoResponseDTO();
    }

    @Test
    public void testCriarComparacao_Success() {
        when(resultadoService.buscarDadosCovidLocalidade(eq("Campinas"), eq("SP"), eq(TipoLocalidade.MUNICIPIO), any(), any())).thenReturn(resultados1);
        when(resultadoService.buscarDadosCovidLocalidade(eq("Linhares"), eq("ES"), eq(TipoLocalidade.MUNICIPIO), any(), any())).thenReturn(resultados2);
        when(comparacaoRepository.save(any(ComparacaoDTO.class))).thenReturn(comparacaoDTO);
        when(convert.convertToResponseDTO(any(ComparacaoDTO.class))).thenReturn(comparacaoResponseDTO);

        ComparacaoResponseDTO result = comparadorService.criarComparacao(benchMarkMock);

        assertNotNull(result);
        verify(resultadoService, times(2)).buscarDadosCovidLocalidade(anyString(), anyString(), any(), any(), any());
        verify(comparacaoRepository).save(any(ComparacaoDTO.class));
        verify(convert).convertToResponseDTO(any(ComparacaoDTO.class));
    }

    @Test
    public void testBuscarPorBenchmark_NotFound_ThrowsException() {
        when(comparacaoRepository.findByBenchmarkId(anyLong())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            comparadorService.buscarPorBenchmark(1L);
        });

        assertTrue(exception.getMessage().contains("BenchMark não encontrado"));
        verify(comparacaoRepository).findByBenchmarkId(1L);
    }

    @Test
    public void testDeletarComparacao_Success() {
        when(comparacaoRepository.findById(anyLong())).thenReturn(Optional.of(comparacaoDTO));
        doNothing().when(comparacaoRepository).deleteById(anyLong());

        comparadorService.deletarComparacao(1L);

        verify(comparacaoRepository).findById(1L);
        verify(comparacaoRepository).deleteById(1L);
    }
}
