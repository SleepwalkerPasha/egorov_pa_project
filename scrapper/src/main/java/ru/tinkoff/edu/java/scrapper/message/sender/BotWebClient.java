package ru.tinkoff.edu.java.scrapper.message.sender;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.exception.ApiErrorException;

public class BotWebClient implements LinkUpdateSender {

    private final WebClient client;

    public BotWebClient(WebClient.Builder builder, @NotNull String url) {
        this.client = builder
                .baseUrl(url)
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError())
                        return clientResponse
                                .bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new ApiErrorException(body)));
                    return Mono.just(clientResponse);
                }))
                .build();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public void linkUpdate(LinkUpdateRequest request) {
        client.post()
                .uri("/updates")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
