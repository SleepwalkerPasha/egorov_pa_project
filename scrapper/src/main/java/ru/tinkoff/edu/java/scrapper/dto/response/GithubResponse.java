package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record GithubResponse(@NotNull @JsonProperty(value = "full_name") String fullName,
                             @NotNull @JsonProperty(value = "pushed_at") OffsetDateTime pushedAt,
                             @NotNull @JsonProperty(value = "updated_at") OffsetDateTime updatedAt,
                             @NotNull @JsonProperty(value = "open_issues_count") Integer openIssuesCount,
                             @NotNull @JsonProperty(value = "forks") Integer forksCount) implements Response {

}
