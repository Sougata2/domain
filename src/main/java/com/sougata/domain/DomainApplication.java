package com.sougata.domain;

import com.sougata.domain.auth.jwt.JwtProperties;
import com.sougata.domain.file.properties.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class, FileProperties.class})
public class DomainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainApplication.class, args);
    }

}
