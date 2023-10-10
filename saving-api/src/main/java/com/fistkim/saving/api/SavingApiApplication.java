package com.fistkim.saving.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fistkim.saving"})
public class SavingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SavingApiApplication.class, args);
    }

}
