package ru.tinkoff.edu.java.scrapper.message.sender;

import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;


public interface LinkUpdateSender {

    void linkUpdate(LinkUpdateRequest request);
}
