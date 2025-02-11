package com.backend.BenchMarks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.BenchMarks.service.CovidService;

@RestController
@RequestMapping("/covid")
public class CovidController{

    @Autowired
    private CovidService covidService;

    @GetMapping("/{estado}")
    public String getCovidData(@PathVariable String estado) {
        return covidService.getCovidData(estado);
    }
}