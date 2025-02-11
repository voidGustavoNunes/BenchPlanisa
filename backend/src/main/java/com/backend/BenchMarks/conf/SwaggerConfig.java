package com.backend.BenchMarks.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customApi(){
        return new OpenAPI().info(new Info().title("Projeto BenchMark Planisa").version("1.0.0")
                                    .description("Projeto de Benchmark de casos de Covid-19"));
    }
}
