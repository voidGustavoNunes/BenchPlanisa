package com.backend.BenchMarks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.BenchMarks.model.BenchMark;
import com.backend.BenchMarks.service.BenchMarkService;
import com.backend.BenchMarks.service.GenericService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Validated
@RestController
@RequestMapping("/api/benchmark")
@Tag(name = "BenchMark", description = "Operações relacionadas aos benchmarks")
public class BenchMarkController extends GenericController<BenchMark>{

    @Autowired
    BenchMarkService benchMarkService;

    protected BenchMarkController(GenericService<BenchMark> genericService) {
        super(genericService);
    }
    


}
