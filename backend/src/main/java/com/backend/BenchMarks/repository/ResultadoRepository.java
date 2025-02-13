package com.backend.BenchMarks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.BenchMarks.model.dto.ResultadoDTO;

public interface ResultadoRepository extends JpaRepository<ResultadoDTO, Long>{
    
}
