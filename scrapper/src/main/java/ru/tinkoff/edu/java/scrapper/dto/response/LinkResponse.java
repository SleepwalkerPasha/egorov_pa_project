package ru.tinkoff.edu.java.scrapper.dto.response;

import jakarta.validation.constraints.NotNull;

public record LinkResponse(Long id, @NotNull String url) {
}
