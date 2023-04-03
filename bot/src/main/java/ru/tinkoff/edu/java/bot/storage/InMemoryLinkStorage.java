package ru.tinkoff.edu.java.bot.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Qualifier("InMemoryLinkStorage")
public class InMemoryLinkStorage implements LinkStorage {

    private final Map<Long, Set<String>> map;

    public InMemoryLinkStorage() {
        this.map = new ConcurrentHashMap<>();
    }

    public void registerUser(Long userId, Set<String> links) {
        map.putIfAbsent(userId, links);
    }

    public void untrackLink(Long userId, String link) {
        Set<String> links = map.get(userId);
        if (links != null)
            map.get(userId).remove(link);
    }

    public Set<String> getFollowedLinks(Long userId) {
        Set<String> links = map.get(userId);
        if (links != null)
            return map.get(userId);
        return new HashSet<>();
    }

    public void trackLink(Long userId, String link) {
        Set<String> links = map.get(userId);
        if (links != null)
            map.get(userId).add(link);
    }
}
