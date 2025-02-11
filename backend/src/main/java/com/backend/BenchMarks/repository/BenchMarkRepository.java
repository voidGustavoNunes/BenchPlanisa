package com.backend.BenchMarks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.BenchMarks.model.BenchMark;

@Repository
public interface BenchMarkRepository extends JpaRepository<BenchMark, Long>{
    
}
