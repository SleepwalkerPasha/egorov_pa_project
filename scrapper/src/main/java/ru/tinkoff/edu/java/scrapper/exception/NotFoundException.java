package ru.tinkoff.edu.java.scrapper.exception;

public class NotFoundException extends ApiErrorException {

    public NotFoundException(String message) {
        super(message);
        this.description = "Ресурс не был найден";
    }

}
