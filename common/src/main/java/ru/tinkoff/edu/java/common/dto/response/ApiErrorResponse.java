package ru.tinkoff.edu.java.common.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse {
    private final String description;

    private final String code;

    private final String exceptionName;

    private final String exceptionMessage;

    private final List<String> stacktrace;
}
