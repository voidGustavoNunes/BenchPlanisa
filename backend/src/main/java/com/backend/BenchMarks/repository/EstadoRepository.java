package com.backend.BenchMarks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.BenchMarks.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findBySigla(String sigla);

}
