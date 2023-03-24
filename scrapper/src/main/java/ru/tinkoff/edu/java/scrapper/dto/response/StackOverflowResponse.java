package ru.tinkoff.edu.java.scrapper.dto.response;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record StackOverflowResponse(@NotNull String link, @NotNull OffsetDateTime lastActivityDate) {
}
