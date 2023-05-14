package ru.tinkoff.edu.java.bot.exception;

import jakarta.validation.constraints.NotNull;

public class ApiErrorException extends RuntimeException {
    @NotNull
    private final String description = "Неверные параметры запроса";

    public ApiErrorException(String message) {
        super(message);
    }

    public String getDescription() {
        return description;
    }
}
