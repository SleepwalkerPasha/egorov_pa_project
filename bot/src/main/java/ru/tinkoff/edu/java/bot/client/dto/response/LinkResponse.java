package ru.tinkoff.edu.java.bot.client.dto.response;

import jakarta.validation.constraints.NotNull;

import java.net.URI;
import java.util.Objects;

public final class LinkResponse {
    private final Long id;
    private final @NotNull URI url;

    public LinkResponse(Long id, @NotNull String url) {
        this.id = id;
        this.url = URI.create(url);
    }

    public Long id() {
        return id;
    }

    public @NotNull URI url() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (LinkResponse) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }

    @Override
    public String toString() {
        return "LinkResponse[" +
                "id=" + id + ", " +
                "url=" + url + ']';
    }


}
