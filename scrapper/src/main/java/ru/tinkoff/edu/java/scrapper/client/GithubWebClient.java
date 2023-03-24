package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.response.GithubResponse;

public class GithubWebClient implements GithubClient {

    private final WebClient githubClient;

    private GithubWebClient(WebClient.Builder githubClientBuilder) {
        this.githubClient = githubClientBuilder
                .baseUrl("https://api.github.com")
                .defaultHeader("Accept", "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
    }

    private GithubWebClient(WebClient.Builder githubClientBuilder, String baseUrl) {
        this.githubClient = githubClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
    }

    public static GithubWebClient getInstance(WebClient.Builder githubClientBuilder, String url) {
        if (url == null)
            return new GithubWebClient(githubClientBuilder);
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
