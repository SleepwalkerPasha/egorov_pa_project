package ru.tinkoff.edu.java.scrapper.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record StackOverflowItemsResponse(@NotNull List<StackOverflowResponse> items) {
}
