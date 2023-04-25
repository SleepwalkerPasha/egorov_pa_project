package ru.tinkoff.edu.parser.dto;

public sealed interface ParseResult permits GithubResult, StackOverflowResult {

    String resultType();
}
