package ru.tinkoff.edu.parser;


import java.net.URI;
import ru.tinkoff.edu.parser.dto.ParseResult;


public sealed interface IParser permits AbstractParser {
    ParseResult parseUrl(URI url);
}
