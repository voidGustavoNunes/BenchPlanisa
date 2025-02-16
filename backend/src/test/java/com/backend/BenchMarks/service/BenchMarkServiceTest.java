package com.backend.BenchMarks.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import com.backend.BenchMarks.handler.BusinessException;
import com.backend.BenchMarks.handler.RegistroNotFoundException;
import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.model.dto.ComparacaoResponseDTO;
import com.backend.BenchMarks.model.enums.TipoLocalidade;
import com.backend.BenchMarks.repository.BenchMarkRepository;
import com.backend.BenchMarks.util.Convert;


//Alguns testes unitarios
public class BenchMarkServiceTest {

    @Mock
    private BenchMarkRepository repository;

    @Mock
    private ComparadorService comparadorService;

    @Mock
    private Convert convert;

    @InjectMocks
    private BenchMarkService benchMarkService;

    private BenchMark benchMarkMock;
    private ComparacaoResponseDTO comparacaoResponseMock;
    private ComparacaoDTO comparacaoDTOMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        benchMarkMock = new BenchMark();
        benchMarkMock.setId(1L);
        benchMarkMock.setNome("Benchmark teste");
        benchMarkMock.setLocalidade1("Campinas");
        benchMarkMock.setLocalidade2("Linhares");
        benchMarkMock.setEstadoLocalidade1("SP");
        benchMarkMock.setEstadoLocalidade2("ES");
        benchMarkMock.setDataInicial(LocalDate.of(2020, 3, 1));
        benchMarkMock.setDataFinal(LocalDate.of(2020, 6, 1));
        benchMarkMock.setTipoLocalidade(TipoLocalidade.MUNICIPIO);

        comparacaoResponseMock = new ComparacaoResponseDTO();
        comparacaoDTOMock = new ComparacaoDTO();
        comparacaoDTOMock.setId(1L);
        comparacaoDTOMock.setBenchmark(benchMarkMock);
    }

    @Test
    public void testSaveValidation_DataInicialMaiorQueDataFinal_ThrowsException() {
        BenchMark benchMarkInvalido = new BenchMark();
        BeanUtils.copyProperties(benchMarkMock, benchMarkInvalido);
        benchMarkInvalido.setDataInicial(LocalDate.of(2020, 7, 1));
        benchMarkInvalido.setDataFinal(LocalDate.of(2020, 6, 1));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            benchMarkService.saveValidation(benchMarkInvalido);
        });

        assertTrue(exception.getMessage().contains("A data inicial deve ser maior que a data final"));
    }

    @Test
    public void testCriar_NomeJaCadastrado_ThrowsException() {
        when(repository.findByNome(anyString())).thenReturn(Optional.of(benchMarkMock));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            benchMarkService.criar(benchMarkMock);
        });

        assertTrue(exception.getMessage().contains("Já existe um benchmark cadastrado com esse nome"));
        verify(repository).findByNome(benchMarkMock.getNome());
    }

    @Test
    public void testCriar_Success() {
        when(repository.findByNome(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(BenchMark.class))).thenReturn(benchMarkMock);
        when(comparadorService.criarComparacao(any(BenchMark.class))).thenReturn(comparacaoResponseMock);
        when(convert.convertToComparacaoDTO(any(ComparacaoResponseDTO.class))).thenReturn(comparacaoDTOMock);

        BenchMark result = benchMarkService.criar(benchMarkMock);

        assertNotNull(result);
        assertEquals(benchMarkMock.getId(), result.getId());
        assertEquals(benchMarkMock.getNome(), result.getNome());
        assertNotNull(result.getComparacao());
        verify(repository).findByNome(benchMarkMock.getNome());
        verify(repository, times(2)).save(any(BenchMark.class));
        verify(comparadorService).criarComparacao(any(BenchMark.class));
        verify(convert).convertToComparacaoDTO(any(ComparacaoResponseDTO.class));
    }

    @Test
    public void testAtualizar_RegistroNaoEncontrado_ThrowsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RegistroNotFoundException.class, () -> {
            benchMarkService.atualizar(1L, benchMarkMock);
        });

        verify(repository).findById(1L);
    }

    @Test
    public void testAtualizar_NomeJaCadastrado_ThrowsException() {
        BenchMark existente = new BenchMark();
        existente.setId(2L);
        existente.setNome("Outro benchmark");
        
        when(repository.findById(anyLong())).thenReturn(Optional.of(existente));
        when(repository.findByNome(anyString())).thenReturn(Optional.of(benchMarkMock));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            benchMarkService.atualizar(2L, benchMarkMock);
        });

        assertTrue(exception.getMessage().contains("Já existe um benchmark cadastrado com esse nome"));
        verify(repository).findById(2L);
        verify(repository).findByNome(benchMarkMock.getNome());
    }

    @Test
    public void testAtualizar_Success_SemModificarDadosComparacao() throws Exception {
        BenchMark existente = new BenchMark();
        BeanUtils.copyProperties(benchMarkMock, existente);
        existente.setComparacao(comparacaoDTOMock);
        
        BenchMark atualizado = new BenchMark();
        atualizado.setId(1L);
        atualizado.setNome("Benchmark atualizado");
        
        when(repository.findById(anyLong())).thenReturn(Optional.of(existente));
        when(repository.findByNome(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(BenchMark.class))).thenReturn(existente);

        BenchMark result = benchMarkService.atualizar(1L, atualizado);

        assertNotNull(result);
        assertEquals("Benchmark atualizado", result.getNome());
        assertNotNull(result.getComparacao());
        verify(repository).findById(1L);
        verify(repository).findByNome("Benchmark atualizado");
        verify(repository).save(any(BenchMark.class));
        verify(comparadorService, never()).criarComparacao(any(BenchMark.class));
    }

    @Test
    public void testAtualizar_Success_ModificandoDadosComparacao() throws Exception {
        BenchMark existente = new BenchMark();
        BeanUtils.copyProperties(benchMarkMock, existente);
        existente.setComparacao(comparacaoDTOMock);
        
        BenchMark atualizado = new BenchMark();
        atualizado.setId(1L);
        atualizado.setLocalidade1("Nova localidade");
        
        when(repository.findById(anyLong())).thenReturn(Optional.of(existente));
        when(repository.save(any(BenchMark.class))).thenReturn(existente);
        when(comparadorService.criarComparacao(any(BenchMark.class))).thenReturn(comparacaoResponseMock);
        when(convert.convertToComparacaoDTO(any(ComparacaoResponseDTO.class))).thenReturn(comparacaoDTOMock);

        BenchMark result = benchMarkService.atualizar(1L, atualizado);

        assertNotNull(result);
        assertEquals("Nova localidade", result.getLocalidade1());
        assertNotNull(result.getComparacao());
        verify(repository).findById(1L);
        verify(repository, times(2)).save(any(BenchMark.class));
        verify(comparadorService).criarComparacao(any(BenchMark.class));
        verify(convert).convertToComparacaoDTO(any(ComparacaoResponseDTO.class));
    }
}