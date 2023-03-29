package ru.tinkoff.edu.java.scrapper.exception;

import jakarta.validation.constraints.NotNull;

public class ApiErrorException extends RuntimeException {

    @NotNull
    protected String description = "Ошибка в API";

    public ApiErrorException(String message) {
        super(message);
    }

    public String getDescription() {
        return description;
    }
}
