package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkUpdateRequest {

    @NotNull
    private final Long id;

    @NotNull
    private final String url;

    @NotNull
    private final String description;

    @NotNull
    private final List<Long> tgChatIds;
}
