package ru.tinkoff.edu.parser;

import ru.tinkoff.edu.parser.entities.ParseResult;

import java.net.URI;

public sealed interface IParser permits AbstractParser{
    ParseResult parseUrl(URI url);
}
