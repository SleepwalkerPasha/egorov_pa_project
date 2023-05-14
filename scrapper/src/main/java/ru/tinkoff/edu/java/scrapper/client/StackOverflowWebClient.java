package ru.tinkoff.edu.java.scrapper.client;

import java.util.Objects;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowItemsResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

public final class StackOverflowWebClient implements StackOverflowClient {

    private final WebClient stackOverflowClient;

    private StackOverflowWebClient(WebClient.Builder webClientBuilder) {
        this.stackOverflowClient = webClientBuilder
            .baseUrl("https://api.stackexchange.com/2.3")
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create().compress(true)))
            .build();
    }

    private StackOverflowWebClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.stackOverflowClient = webClientBuilder
            .baseUrl(baseUrl)
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create().compress(true)))
            .build();
    }

    public static StackOverflowWebClient getInstance(WebClient.Builder webClientBuilder, String baseUrl) {
        if (baseUrl == null) {
            return new StackOverflowWebClient(webClientBuilder);
        }
        return new StackOverflowWebClient(webClientBuilder, baseUrl);
    }

    @Override
    public StackOverflowResponse fetchQuestion(String id) {
        return Objects.requireNonNull(stackOverflowClient
                .get()
                .uri("/questions/{id}?site=stackoverflow", id)
                .retrieve()
                .bodyToMono(StackOverflowItemsResponse.class)
                .block())
            .items()
            .get(0);
    }
}
