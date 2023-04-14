package ru.tinkoff.edu.java.scrapper.dto.db;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.OffsetDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    private Long id;

    @NotNull
    private URI url;

    @NotNull
    private Long tgId;

    private OffsetDateTime checkedAt;

    private OffsetDateTime updatedAt;
}
