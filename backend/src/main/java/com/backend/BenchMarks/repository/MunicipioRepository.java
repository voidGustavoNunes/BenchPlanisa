package com.backend.BenchMarks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.BenchMarks.model.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Long>{
    List<Municipio> findByEstado_Sigla(String sigla);
}
