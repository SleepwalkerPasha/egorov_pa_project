package ru.tinkoff.edu.java.scrapper.dto.response;

import java.net.URI;

public record Link(URI url, Long tgId) {
}
