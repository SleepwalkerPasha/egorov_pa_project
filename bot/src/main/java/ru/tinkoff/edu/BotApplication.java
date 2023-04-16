package ru.tinkoff.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.configuration.BotProperties;
import ru.tinkoff.edu.java.bot.configuration.ScrapperClientConfig;
import ru.tinkoff.edu.java.bot.bot.Bot;
import ru.tinkoff.edu.java.bot.bot.TelegramRefresherBot;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, BotProperties.class, ScrapperClientConfig.class})
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);

        Bot bot = ctx.getBean(TelegramRefresherBot.class);
        bot.start();
    }
}