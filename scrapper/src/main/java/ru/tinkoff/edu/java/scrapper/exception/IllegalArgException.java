package ru.tinkoff.edu.java.scrapper.exception;

public class IllegalArgException extends ApiErrorException {
    public IllegalArgException(String message) {
        super(message);
        this.description = "Конфликт в выполнении с данным объектом";
    }
}
