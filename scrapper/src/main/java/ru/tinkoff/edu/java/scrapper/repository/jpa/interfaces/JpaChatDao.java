package ru.tinkoff.edu.java.scrapper.repository.jpa.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.edu.java.scrapper.entity.ChatEntity;

import java.util.Collection;

public interface JpaChatDao extends JpaRepository<ChatEntity, Long> {

    @Query(value = "SELECT l.tgId FROM LinkEntity AS l LEFT JOIN ChatEntity c on c.id = l.tgId.id WHERE l.id = :linkId")
    Collection<Long> findAllByLinkId(@Param("linkId") long linkId);

}
