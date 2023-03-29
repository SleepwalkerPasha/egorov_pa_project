package ru.tinkoff.edu.java.scrapper.exception;

public class BadRequestException extends ApiErrorException{

    public BadRequestException(String message) {
        super(message);
        this.description = "Неверные параметры запроса";
    }

}
