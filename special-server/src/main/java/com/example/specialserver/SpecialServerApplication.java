package com.example.specialserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.specialserver.*")
@EnableCaching
@EnableScheduling
public class SpecialServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecialServerApplication.class, args);
    }

}
