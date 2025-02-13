package com.backend.BenchMarks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.BenchMarks.handler.RegistroNotFoundException;
import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.model.dto.ComparacaoDTO;
import com.backend.BenchMarks.repository.BenchMarkRepository;

@Service
public class BenchMarkService extends GenericServiceImpl<BenchMark, BenchMarkRepository>{

    @Autowired
    private LocalidadeService localidadeService;

    @Autowired 
    private ComparadorService comparadorService;

    protected BenchMarkService(BenchMarkRepository repository) {
        super(repository);
    }
    
    @Override
    public void saveValidation(BenchMark entity) throws RegistroNotFoundException {
        
    }

    @Override
    public BenchMark criar(BenchMark entity) { //implementar businessexception
        BenchMark benchMark = new BenchMark();
        benchMark.setNome(entity.getNome());
        benchMark.setLocalidade1(localidadeService.buscarPorId(entity.getLocalidade1().getId()));
        benchMark.setLocalidade2(localidadeService.buscarPorId(entity.getLocalidade2().getId()));
        benchMark.setDataInicial(entity.getDataInicial());
        benchMark.setDataFinal(entity.getDataFinal());
        benchMark.setTipoLocalidade(entity.getTipoLocalidade());

        ComparacaoDTO comparacao = comparadorService.criarComparacao(benchMark);
        benchMark.setComparacao(comparacao);

        this.saveValidation(benchMark);
        return repository.save(benchMark);
    }




    
}
