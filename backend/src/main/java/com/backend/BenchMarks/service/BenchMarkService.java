package com.backend.BenchMarks.service;


import org.springframework.stereotype.Service;
import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.repository.BenchMarkRepository;
import com.backend.BenchMarks.util.RegistroNotFoundException;



@Service
public class BenchMarkService extends GenericServiceImpl<BenchMark, BenchMarkRepository>{

    protected BenchMarkService(BenchMarkRepository repository) {
        super(repository);
    }
    
    @Override
    public void saveValidation(BenchMark entity) throws RegistroNotFoundException {
        
    }

    
}
