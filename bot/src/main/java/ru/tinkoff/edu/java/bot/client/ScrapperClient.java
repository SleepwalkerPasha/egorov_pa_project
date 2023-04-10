package ru.tinkoff.edu.java.bot.client;


import ru.tinkoff.edu.java.bot.client.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.client.dto.response.ListLinkResponse;

public interface ScrapperClient {

    void registerNewChat(Long id);

    void deleteChat(Long id);

    ListLinkResponse getFollowedLinks(Long id);

    LinkResponse addLinkToFollowing(Long id, String url);

    LinkResponse removeLinkFromFollowing(Long id, String url);
}
