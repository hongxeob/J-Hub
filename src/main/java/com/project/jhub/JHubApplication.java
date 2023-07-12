package com.project.jhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(JHubApplication.class, args);
    }

}
