package ru.tinkoff.edu.parser;

import ru.tinkoff.edu.parser.dto.GithubResult;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GithubParser extends AbstractParser {

    private final Pattern pattern;

    public GithubParser() {
        pattern = Pattern.compile("^[A-Za-z0-9\\-\\s]*$");
        host = "github.com";
    }

    @Override
    public GithubResult parseUrl(URI url) {
        if (!url.getHost().equals(host))
            return null;
        String path = url.getPath();
        if (path == null)
            return null;
        Matcher matcher;
        String[] split = path.split("/");
        for (String s : split) {
            if (!s.isBlank()) {
                matcher = pattern.matcher(s);
                if (!matcher.matches())
                    return null;
            }
        }
        String username = split[1];
        String reponame = split[2];
        return new GithubResult(username, reponame);
    }
}
