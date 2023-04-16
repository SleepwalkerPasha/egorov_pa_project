package ru.tinkoff.edu.java.scrapper.dto.db;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkInfo {

    @NotNull
    private Long id;

    private Integer forksCount;

    private Integer answerCount;

    private Integer openIssuesCount;

    private Integer commentCount;

}
