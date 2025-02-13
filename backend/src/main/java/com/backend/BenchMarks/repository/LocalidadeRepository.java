package com.backend.BenchMarks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.BenchMarks.model.Localidade;

public interface LocalidadeRepository extends JpaRepository<Localidade, Long>{
    
}
