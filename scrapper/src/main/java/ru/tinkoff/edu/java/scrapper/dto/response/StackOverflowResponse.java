package ru.tinkoff.edu.java.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record StackOverflowResponse(@NotNull String link,
                                    @NotNull @JsonProperty(value = "last_activity_date")
                                    OffsetDateTime lastActivityDate,
                                    @NotNull @JsonProperty(value = "answer_count") Integer answerCount,
                                    @JsonProperty(value = "comment_count") Integer commentCount) implements Response {
}
