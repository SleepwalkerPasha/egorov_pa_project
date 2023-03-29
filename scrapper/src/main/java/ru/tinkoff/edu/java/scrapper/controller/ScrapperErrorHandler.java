package ru.tinkoff.edu.java.scrapper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.ApiErrorException;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.exception.IllegalArgException;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ScrapperErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequest(final BadRequestException e) {
        return exceptionToResponse(e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(final NotFoundException e) {
        return exceptionToResponse(e);
    }

    private ApiErrorResponse exceptionToResponse(final ApiErrorException e) {
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleConflict(final IllegalArgException e) {
        return exceptionToResponse(e);
    }
}
