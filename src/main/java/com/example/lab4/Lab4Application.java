package com.example.lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:hits.properties")
public class Lab4Application  {

    public static void main(String[] args) {
        SpringApplication.run(Lab4Application.class, args);
    }

}
