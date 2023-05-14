package ru.tinkoff.edu.java.bot.client;


import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.client.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.bot.client.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.bot.client.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.client.dto.response.ListLinkResponse;
import ru.tinkoff.edu.java.bot.exception.ApiErrorException;

public class ScrapperWebClient implements ScrapperClient {

    private final WebClient client;
    private final String headerName = "Tg-Chat-Id";
    private final String uriForTgChat = "/tg-chat/{id}";
    private final String linksUri = "/links";

    public ScrapperWebClient(WebClient.Builder builder, @NotNull String url) {
        this.client = builder
                .baseUrl(url)
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    if (clientResponse.statusCode().is4xxClientError()) {
                        return clientResponse
                                .bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new ApiErrorException(body)));
                    }
                    return Mono.just(clientResponse);
                }))
                .build();
    }

    @Override
    public void registerNewChat(Long id) {
        client.post()
                .uri(uriForTgChat, id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void deleteChat(Long id) {
        client.delete()
                .uri(uriForTgChat, id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public ListLinkResponse getFollowedLinks(Long id) {
        return client.get()
                .uri(linksUri)
                .header(headerName, Long.toString(id))
                .retrieve()
                .bodyToMono(ListLinkResponse.class)
                .block();
    }


    @Override
    public LinkResponse addLinkToFollowing(Long id, String url) {
        return client.post()
                .uri(linksUri)
                .header(headerName, Long.toString(id))
                .body(BodyInserters.fromValue(new AddLinkRequest(url)))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    @Override
    public LinkResponse removeLinkFromFollowing(Long id, String url) {
        return client.method(HttpMethod.DELETE)
                .uri(linksUri)
                .header(headerName, Long.toString(id))
                .body(BodyInserters.fromValue(new RemoveLinkRequest(url)))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }
}
