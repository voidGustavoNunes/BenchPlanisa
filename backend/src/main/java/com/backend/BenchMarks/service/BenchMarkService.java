package com.backend.BenchMarks.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.BenchMarks.handler.BusinessException;
import com.backend.BenchMarks.handler.RegistroNotFoundException;
import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.model.dto.ComparacaoResponseDTO;
import com.backend.BenchMarks.repository.BenchMarkRepository;
import com.backend.BenchMarks.util.Convert;
import java.lang.reflect.Field;

@Service
public class BenchMarkService extends GenericServiceImpl<BenchMark, BenchMarkRepository>{
    @Autowired
    private ComparadorService comparadorService;

    @Autowired
    private Convert convert;

    protected BenchMarkService(BenchMarkRepository repository) {
        super(repository);
    }
    
    @Override
    public void saveValidation(BenchMark entity) throws BusinessException {
        if (!entity.getDataFinal().isAfter(entity.getDataInicial())) {
            throw new BusinessException("A data inicial deve ser maior que a data final no cadastro da benchmark de id: ." + entity.getId());
        }
    }

    @Override
    public BenchMark criar(BenchMark entity) {
        validarBenchmarkExistente(entity.getNome());
        
        BenchMark benchMark = criarEPersistirBenchmark(entity);
        ComparacaoDTO comparacao = criarEAssociarComparacao(benchMark);
        
        return persistirRelacionamento(benchMark, comparacao);
    }
    
    private void validarBenchmarkExistente(String nome) {
        Optional<BenchMark> existingBenchMark = repository.findByNome(nome);
        if (existingBenchMark.isPresent()) {
            throw new BusinessException("Já existe um benchmark cadastrado com esse nome: " + nome);
        }
    }

    private BenchMark criarEPersistirBenchmark(BenchMark entity) {
        BenchMark benchMark = new BenchMark();
        BeanUtils.copyProperties(entity, benchMark);
        
        this.saveValidation(benchMark);
        return repository.save(benchMark);
    }

    private ComparacaoDTO criarEAssociarComparacao(BenchMark benchMark) {
        ComparacaoResponseDTO comparacaoResponse = comparadorService.criarComparacao(benchMark);
        ComparacaoDTO comparacaoDTO = convert.convertToComparacaoDTO(comparacaoResponse);
        comparacaoDTO.setBenchmark(benchMark);
        return comparacaoDTO;
    }

    private BenchMark persistirRelacionamento(BenchMark benchMark, ComparacaoDTO comparacaoDTO) {
        benchMark.setComparacao(comparacaoDTO);
        return repository.save(benchMark);
    }



    @Override
    public BenchMark atualizar(Long id, BenchMark entityAtualizada) {
        // Validação inicial
        this.saveValidation(entityAtualizada);
        
        // Busca entidade existente
        BenchMark benchmarkExistente = repository.findById(id)
                .orElseThrow(() -> new RegistroNotFoundException(id));

        // Verifica se já existe outro benchmark com o mesmo nome (exceto o atual)
        if (entityAtualizada.getNome() != null && !entityAtualizada.getNome().equals(benchmarkExistente.getNome())) {
            Optional<BenchMark> existingBenchMark = repository.findByNome(entityAtualizada.getNome());
            if (existingBenchMark.isPresent()) {
                throw new BusinessException("Já existe um benchmark cadastrado com esse nome: " + entityAtualizada.getNome());
            }
        }

        try {
            Class<?> clazz = benchmarkExistente.getClass();
            Field[] fields = clazz.getDeclaredFields();
            boolean dadosComparacaoModificados = false;

            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entityAtualizada);
                
                if (value != null) {
                    // Verifica se algum campo que afeta a comparação foi modificado
                    if (isCampoComparacao(field.getName())) {
                        Object valorAntigo = field.get(benchmarkExistente);
                        if (!value.equals(valorAntigo)) {
                            dadosComparacaoModificados = true;
                        }
                    }
                    field.set(benchmarkExistente, value);
                }
            }

            // Se os dados que afetam a comparação foram modificados, recria a comparação
            if (dadosComparacaoModificados) {
                // Remove a comparação antiga se existir
                if (benchmarkExistente.getComparacao() != null) {
                    ComparacaoDTO oldComparacao = benchmarkExistente.getComparacao();
                    benchmarkExistente.setComparacao(null);
                    repository.save(benchmarkExistente); // Salva antes de excluir a comparação

                }
                // Cria nova comparação
                ComparacaoResponseDTO novaComparacao = comparadorService.criarComparacao(benchmarkExistente);
                ComparacaoDTO comparacaoDTO = convert.convertToComparacaoDTO(novaComparacao);
                comparacaoDTO.setBenchmark(benchmarkExistente);
                benchmarkExistente.setComparacao(comparacaoDTO);
            }

            return repository.save(benchmarkExistente);
            
        } catch (IllegalAccessException e) {
            throw new BusinessException("Erro ao atualizar o benchmark: " + e.getMessage());
        }
    }

    private boolean isCampoComparacao(String fieldName) {
        return Arrays.asList(
            "localidade1",
            "localidade2",
            "estadoLocalidade1",
            "estadoLocalidade2",
            "tipoLocalidade",
            "dataInicial",
            "dataFinal"
        ).contains(fieldName);
    }
} 