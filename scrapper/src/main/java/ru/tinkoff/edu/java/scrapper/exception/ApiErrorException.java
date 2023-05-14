package ru.tinkoff.edu.java.scrapper.exception;

import jakarta.validation.constraints.NotNull;

public class ApiErrorException extends RuntimeException {

    @SuppressWarnings("checkstyle:MutableException") @NotNull
    protected String description;

    public ApiErrorException(String message) {
        super(message);
        description = "Ошибка в API";
    }

    public String getDescription() {
        return description;
    }
}
