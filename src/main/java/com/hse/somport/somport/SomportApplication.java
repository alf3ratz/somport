package com.hse.somport.somport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages={"com.hse.somport"})
@EnableJpaRepositories(basePackages="com.hse.somport.somport.entities")
@EnableTransactionManagement
@EntityScan(basePackages="com.hse.somport.somport.entities")
public class SomportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SomportApplication.class, args);
    }

}
