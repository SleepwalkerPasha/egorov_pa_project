package ru.tinkoff.edu.parser;

import ru.tinkoff.edu.parser.dto.StackOverflowResult;

import java.net.URI;

public final class StackOverflowParser extends AbstractParser {

    public StackOverflowParser() {
        host = "stackoverflow.com";
    }

    @Override
    public StackOverflowResult parseUrl(URI url) {
        if (!url.getHost().equals(host))
            return null;
        String path = url.getPath();
        if (path == null)
            return null;
        if (!path.contains("questions"))
            return null;
        String[] split = path.split("/");
        String id = split[2];
        return new StackOverflowResult(Integer.parseInt(id));
    }
}
