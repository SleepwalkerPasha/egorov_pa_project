package ru.tinkoff.edu.java.bot.storage;

import java.util.Set;

public interface LinkStorage {
    void registerUser(Long userId, Set<String> links);

    void untrackLink(Long userId, String link);

    Set<String> getFollowedLinks(Long userId);

    void trackLink(Long userId, String link);
}
