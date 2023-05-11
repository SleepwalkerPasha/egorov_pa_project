package ru.tinkoff.edu.java.common.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LinkUpdateRequest {


    private final Long id;

    private final String url;

    private final String description;

    private final List<Long> tgChatIds;
}
