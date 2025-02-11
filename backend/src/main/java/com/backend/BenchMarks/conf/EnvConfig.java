package com.backend.BenchMarks.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class EnvConfig {

    @Bean
    private Dotenv dotenv() {
        return Dotenv.load();
    }
    
}