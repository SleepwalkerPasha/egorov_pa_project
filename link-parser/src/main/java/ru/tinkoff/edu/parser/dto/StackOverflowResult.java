package ru.tinkoff.edu.parser.dto;

public record StackOverflowResult(Integer id) implements ParseResult {

    @Override
    public String toString() {
        return "StackOverflowResult{" +
                "id=" + id +
                '}';
    }

    @Override
    public String resultType() {
        return "StackOverflowResult";
    }
}
