package ru.tinkoff.edu;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class IntegrationEnvironment {

    @Container
    public static final PostgreSQLContainer<?> DB_CONTAINER = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("scrapper")
        .withUsername("postgres")
        .withPassword("changeme")
        .withExposedPorts(5432);

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add(
            "spring.datasource.url",
            () -> String.format("jdbc:postgresql://localhost:%d/%s",
                DB_CONTAINER.getFirstMappedPort(), DB_CONTAINER.getDatabaseName()
            )
        );
        registry.add("spring.datasource.username", DB_CONTAINER::getUsername);
        registry.add("spring.datasource.password", DB_CONTAINER::getPassword);
    }

    @TestConfiguration
    static class IntegrationEnvironmentConfiguration {
        @Bean
        public DataSource transferDataSource() {
            return DataSourceBuilder.create()
                .url(DB_CONTAINER.getJdbcUrl())
                .username(DB_CONTAINER.getUsername())
                .password(DB_CONTAINER.getPassword())
                .build();
        }

    }

}
