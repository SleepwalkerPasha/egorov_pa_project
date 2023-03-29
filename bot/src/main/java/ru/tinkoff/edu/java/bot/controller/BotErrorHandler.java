package ru.tinkoff.edu.java.bot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.exception.ApiErrorException;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class BotErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequest(final ApiErrorException e) {
        List<String> stacktrace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList();
        log.error(e.getDescription() + ": " + e.getMessage());
        return ApiErrorResponse.builder()
                .description(e.getDescription())
                .code("400")
                .exceptionName(e.getClass().getSimpleName())
                .exceptionMessage(e.getMessage())
                .stacktrace(stacktrace)
                .build();
    }
}
