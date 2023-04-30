package ru.tinkoff.edu.java.common.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiErrorResponse {
    private final String description;

    private final String code;

    private final String exceptionName;

    private final String exceptionMessage;

    private final List<String> stacktrace;
}
