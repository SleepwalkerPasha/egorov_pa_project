package ru.tinkoff.edu.parser.entities;

public final class GithubResult extends ParseResult {

    private final String name;

    private final String repoName;

    public GithubResult(String name, String repoName) {
        this.name = name;
        this.repoName = repoName;
    }

    public String getName() {
        return name;
    }

    public String getRepoName() {
        return repoName;
    }

    @Override
    public String toString() {
        return "GithubResult{" +
                "name='" + name + '\'' +
                ", repoName='" + repoName + '\'' +
                '}';
    }
}
