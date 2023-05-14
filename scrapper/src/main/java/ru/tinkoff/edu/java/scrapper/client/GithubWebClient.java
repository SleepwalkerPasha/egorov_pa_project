package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.response.GithubResponse;

public final class GithubWebClient implements GithubClient {

    private final WebClient githubClient;
    private final String acceptHeader = "Accept";
    private final String acceptHeaderValue = "application/vnd.github+json";
    private final String githubHeader = "X-GitHub-Api-Version";
    private final String githubHeaderValue = "2022-11-28";

    private GithubWebClient(WebClient.Builder githubClientBuilder) {
        this.githubClient = githubClientBuilder
            .baseUrl("https://api.github.com")
            .defaultHeader(acceptHeader, acceptHeaderValue)
            .defaultHeader(githubHeader, githubHeaderValue)
            .build();
    }

    private GithubWebClient(WebClient.Builder githubClientBuilder, String baseUrl) {
        this.githubClient = githubClientBuilder
            .baseUrl(baseUrl)
            .defaultHeader(acceptHeader, acceptHeaderValue)
            .defaultHeader(githubHeader, githubHeaderValue)
            .build();
    }

    public static GithubWebClient getInstance(WebClient.Builder githubClientBuilder, String url) {
        if (url == null || url.isBlank()) {
            return new GithubWebClient(githubClientBuilder);
        }
        return new GithubWebClient(githubClientBuilder, url);
    }

    @Override
    public GithubResponse fetchRepository(String user, String repo) {
        return githubClient.get()
            .uri("/repos/{user}/{repo}", user, repo)
            .retrieve()
            .bodyToMono(GithubResponse.class)
            .block();
    }
}
