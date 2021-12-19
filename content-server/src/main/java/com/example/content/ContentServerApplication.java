package com.example.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableDiscoveryClient
//@EnableEurekaClient
@SpringBootApplication
public class ContentServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServerApplication.class, args);
    }

}
