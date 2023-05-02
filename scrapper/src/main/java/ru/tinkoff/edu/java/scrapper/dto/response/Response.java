package ru.tinkoff.edu.java.scrapper.dto.response;

public sealed interface Response permits GithubResponse, StackOverflowResponse  {
}
