package com.example.managementweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ManagementWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementWebApplication.class, args);
    }

}
