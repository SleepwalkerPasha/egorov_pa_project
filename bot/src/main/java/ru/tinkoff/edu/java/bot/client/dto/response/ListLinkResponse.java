package ru.tinkoff.edu.java.bot.client.dto.response;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ListLinkResponse(@NotNull List<LinkResponse> links, Long size) {
}
