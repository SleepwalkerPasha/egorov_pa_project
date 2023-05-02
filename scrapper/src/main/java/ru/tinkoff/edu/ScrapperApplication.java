package ru.tinkoff.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfigProperties;
import ru.tinkoff.edu.java.scrapper.configuration.ClientConfigProperties;
import ru.tinkoff.edu.java.scrapper.configuration.ClientConfiguration;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfigProperties.class, ClientConfigProperties.class})
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfigProperties config = ctx.getBean(ApplicationConfigProperties.class);
        System.out.println(config);

        ClientConfiguration clientConfiguration = ctx.getBean(ClientConfiguration.class);
        System.out.println(clientConfiguration);

    }
}