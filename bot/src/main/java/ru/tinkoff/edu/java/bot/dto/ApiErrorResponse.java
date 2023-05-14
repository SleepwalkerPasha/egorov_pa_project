package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ApiErrorResponse {
    @NotNull
    private final String description;

    @NotNull
    private final String code;

    @NotNull
    private final String exceptionName;

    @NotNull
    private final String exceptionMessage;

    @NotNull
    private final List<String> stacktrace;
}
