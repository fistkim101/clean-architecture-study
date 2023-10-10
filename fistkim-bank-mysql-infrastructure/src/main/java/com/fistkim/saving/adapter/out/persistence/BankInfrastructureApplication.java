package com.fistkim.saving.adapter.out.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BankInfrastructureApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankInfrastructureApplication.class, args);
    }
}
