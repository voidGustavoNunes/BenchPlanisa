package com.backend.BenchMarks.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.BenchMarks.model.dto.ComparacaoDTO;

@Repository
public interface ComparacaoRepository extends JpaRepository<ComparacaoDTO, Long> {
    Optional<ComparacaoDTO> findByBenchmarkId(Long benchmarkId);
}
