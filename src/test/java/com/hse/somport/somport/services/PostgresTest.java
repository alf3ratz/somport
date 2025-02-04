package com.hse.somport.somport.services;

import com.hse.somport.somport.entities.UserEntity;
import io.restassured.RestAssured;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class PostgresTest {
//
//    @Container
//    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
//            "postgres:16-alpine"
//    ).withReuse(true)
//            .withDatabaseName("somport")
//            .withUsername("somport")
//            .withPassword("somport");
//
//    //    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
////            .withDatabaseName("testdb")
////            .withUsername("testuser")
////            .withPassword("testpassword");
////    @BeforeAll
////    static void beforeAll() {
////        postgres.start();
////    }
////
////    @AfterAll
////    static void afterAll() {
////        postgres.stop();
////    }
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//    }
//
//
////    static {
////        postgres.start();
////        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
////        System.setProperty("spring.datasource.username", postgres.getUsername());
////        System.setProperty("spring.datasource.password", postgres.getPassword());
////    }
//
//
//    @Autowired
//    private UserRepository someRepository;
//
//    @Test
//    void testPostgresDatabase() {
//        someRepository.save(new UserEntity(1L, "fdsfsdf", "fsdf"));
//        assertEquals(1, someRepository.findAll().size());
//    }
}

