package ru.tinkoff.edu.java.scrapper.client;

import ru.tinkoff.edu.java.scrapper.dto.response.GithubResponse;

public interface GithubClient {

    GithubResponse fetchRepository(String user, String repo);
}
