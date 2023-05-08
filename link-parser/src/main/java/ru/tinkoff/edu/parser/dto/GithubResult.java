package ru.tinkoff.edu.parser.dto;

public record GithubResult(String name, String repoName) implements ParseResult {

    @Override
    public String toString() {
        return "GithubResult{"
                + "name='" + name + '\''
                + ", repoName='" + repoName + '\''
                + '}';
    }

    @Override
    public String resultType() {
        return "GithubResult";
    }
}
