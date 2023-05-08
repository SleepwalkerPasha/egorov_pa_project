package ru.tinkoff.edu.parser;

import java.net.URI;
import org.junit.jupiter.api.Test;
import ru.tinkoff.edu.parser.dto.GithubResult;
import ru.tinkoff.edu.parser.dto.ParseResult;
import ru.tinkoff.edu.parser.dto.StackOverflowResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ParserTest {

    static AbstractParser parser = AbstractParser.of(new GithubParser(), new StackOverflowParser());

    @Test
    void shouldReturnGithubResult() {
        URI uri = URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/");

        ParseResult result = parser.parse(uri);
        GithubResult githubResult = (GithubResult) result;

        assertNotNull(result);
        assertEquals("sanyarnd", githubResult.name());
        assertEquals("tinkoff-java-course-2022", githubResult.repoName());
        assertEquals(GithubResult.class, result.getClass());
    }

    @Test
    void shouldReturnStackOverflowResult() {
        URI uri = URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");

        ParseResult result = parser.parse(uri);
        StackOverflowResult stackOverflowResult = (StackOverflowResult) result;

        assertNotNull(result);
        assertEquals("1642028", stackOverflowResult.id().toString());
        assertEquals(StackOverflowResult.class, result.getClass());
    }

    @Test
    void shouldReturnNull() {
        URI uri = URI.create("https://stackoverflow.com/search?q=unsupported%20link");

        ParseResult result = parser.parse(uri);

        assertNull(result);
    }

}
