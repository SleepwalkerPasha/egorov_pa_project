package ru.tinkoff.edu.parser;


import ru.tinkoff.edu.parser.entities.ParseResult;

import java.net.URI;

public abstract sealed class AbstractParser implements IParser permits GithubParser, StackOverflowParser  {

    private ParseResult data;

    private AbstractParser next;

    protected String host;

    public static AbstractParser of(AbstractParser... abstractParsers) {
        for (int i = 0; i < abstractParsers.length - 1; i++) {
            abstractParsers[i].setNext(abstractParsers[i + 1]);
        }
        return abstractParsers[0];
    }

    public final ParseResult parse(URI url) {
        data = parseUrl(url);
        if (next != null && data == null) {
            data = next.parseUrl(url);
        }
        return data;
    }

    public ParseResult getData() {
        return data;
    }

    public void setNext(AbstractParser next) {
        this.next = next;
    }
}
