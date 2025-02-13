package com.backend.BenchMarks.model;

import java.util.List;

import lombok.Data;

@Data
public class APIResponse {
    private Integer count;

    private String next;

    private String previous;
    
    private List<Resultado> results;
}
