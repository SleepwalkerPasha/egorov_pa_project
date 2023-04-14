package ru.tinkoff.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.scrapper.client.GithubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfigProperties;
import ru.tinkoff.edu.java.scrapper.configuration.ClientConfiguration;
import ru.tinkoff.edu.java.scrapper.configuration.ClientConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfigProperties.class, ClientConfigProperties.class})
public class ScrapperApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfigProperties config = ctx.getBean(ApplicationConfigProperties.class);
        System.out.println(config);

        ClientConfiguration clientConfiguration = ctx.getBean(ClientConfiguration.class);
        System.out.println(clientConfiguration);

        GithubClient githubWebClient = ctx.getBean(GithubClient.class);
        System.out.println(githubWebClient.fetchRepository("SleepwalkerPasha", "egorov_pa_project"));

        StackOverflowClient stackOverflowWebClient = ctx.getBean(StackOverflowClient.class);
        System.out.println(stackOverflowWebClient.fetchQuestion("54341037"));
    }
}