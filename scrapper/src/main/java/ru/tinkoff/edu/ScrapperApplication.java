package ru.tinkoff.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfigProperties;
import ru.tinkoff.edu.java.scrapper.configuration.ClientConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfigProperties.class, ClientConfigProperties.class})
public class ScrapperApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ScrapperApplication.class, args);
    }
}
