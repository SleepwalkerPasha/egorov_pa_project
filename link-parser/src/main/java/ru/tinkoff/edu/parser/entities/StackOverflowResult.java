package ru.tinkoff.edu.parser.entities;

public final class StackOverflowResult extends ParseResult {
    private final Integer id;

    public StackOverflowResult(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "StackOverflowResult{" +
                "id=" + id +
                '}';
    }
}
