package ru.tinkoff.edu;

import ru.tinkoff.edu.parser.AbstractParser;
import ru.tinkoff.edu.parser.GithubParser;
import ru.tinkoff.edu.parser.StackOverflowParser;

import java.net.URI;

public class ParserApplication {
    public static void main(String[] args) {
        AbstractParser parser = AbstractParser.of(new GithubParser(), new StackOverflowParser());
        System.out.println(parser.parse(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/")));
        System.out.println(parser.parse(URI.create("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c")));
        System.out.println(parser.parse(URI.create("https://stackoverflow.com/search?q=unsupported%20link")));
    }
}