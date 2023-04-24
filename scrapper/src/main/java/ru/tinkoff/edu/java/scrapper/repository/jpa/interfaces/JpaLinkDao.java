package ru.tinkoff.edu.java.scrapper.repository.jpa.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.edu.java.scrapper.entity.LinkEntity;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

public interface JpaLinkDao extends JpaRepository<LinkEntity, Long> {

    @Query(value = "select l from LinkEntity l where l.tgId.id = :tgChatId")
    Collection<LinkEntity> findAllLinksById(@Param("tgChatId") long tgChatId);

    @Query(value = "select l from LinkEntity l where l.url = :url")
    Optional<LinkEntity> getLink(@Param("url") String url);

    @Query(value = "select l from LinkEntity l where l.checkedAt <= :checkedTime")
    Collection<LinkEntity> findOldLinks(@Param("checkedTime") OffsetDateTime checkedTime);

    @Modifying
    @Query(value = "delete from LinkEntity l where l.url = :url")
    void deleteLinkEntityByUrl(@Param("url") String url);
}
