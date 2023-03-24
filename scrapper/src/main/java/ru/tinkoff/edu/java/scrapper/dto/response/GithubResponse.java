package ru.tinkoff.edu.java.scrapper.dto.response;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record GithubResponse (@NotNull String fullName, @NotNull OffsetDateTime pushedAt,@NotNull OffsetDateTime updatedAt) {

}
