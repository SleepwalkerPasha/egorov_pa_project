package ru.tinkoff.edu.java.scrapper.client;

import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;

public interface StackOverflowClient {

    StackOverflowResponse fetchQuestion(String id);
}
