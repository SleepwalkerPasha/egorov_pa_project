package ru.tinkoff.edu.java.scrapper.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.entity.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.interfaces.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.repository.jpa.interfaces.JpaLinkDao;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Qualifier("JpaLinkRepository")
@RequiredArgsConstructor
public class JpaLinkRepository implements LinkRepository {

    private final JpaLinkDao jpaLinkDao;

    private final JpaChatDao jpaChatDao;

    @Override
    @Transactional
    public Link add(Link link) {
        if (getLink(link).isPresent())
            return link;
        return entityToLink(jpaLinkDao.save(linkToEntity(link)));
    }

    @Override
    public Link remove(Link link) {
        jpaLinkDao.deleteLinkEntityByUrl(link.getUrl().toString());
        return link;
    }

    @Override
    public Link update(Link link) {
        jpaLinkDao.save(linkToEntity(link));
        return link;
    }


    @Override
    public Optional<Link> getLink(Link link) {
        Optional<LinkEntity> linkEntity = jpaLinkDao.getLink(link.getUrl().toString());
        if (linkEntity.isEmpty())
            return Optional.empty();
        return linkEntity.map(this::entityToLink);
    }

    @Override
    public Collection<Link> findAllLinksById(long tgChatId) {
        return jpaLinkDao.findAllLinksById(tgChatId).stream().map(this::entityToLink).collect(Collectors.toList());
    }

    @Override
    public Collection<Link> findAll() {
        return jpaLinkDao.findAll().stream().map(this::entityToLink).collect(Collectors.toList());
    }

    @Override
    public Collection<Link> findOldLinks(OffsetDateTime checkedTime) {
        return jpaLinkDao.findOldLinks(checkedTime).stream().map(this::entityToLink).collect(Collectors.toList());
    }

    private Link entityToLink(LinkEntity entity) {
        if (entity == null)
            return null;
        return new Link(entity.getId(),
                URI.create(entity.getUrl()),
                entity.getTgId().getId(),
                entity.getCheckedAt(),
                entity.getUpdateAt(),
                entity.getForksCount(),
                entity.getAnswerCount(),
                entity.getOpenIssuesCount(),
                entity.getCommentCount());
    }

    private LinkEntity linkToEntity(Link link) {
        if (link == null)
            return null;
        LinkEntity linkEntity = new LinkEntity();
        linkEntity.setId(link.getId());
        linkEntity.setUrl(link.getUrl().toString());
        linkEntity.setTgId(jpaChatDao.findById(link.getTgId()).get());
        linkEntity.setCheckedAt(link.getCheckedAt());
        linkEntity.setUpdateAt(link.getUpdatedAt());
        linkEntity.setForksCount(link.getForksCount());
        linkEntity.setAnswerCount(link.getAnswerCount());
        linkEntity.setOpenIssuesCount(link.getOpenIssuesCount());
        linkEntity.setCommentCount(link.getCommentCount());
        return linkEntity;
    }
}
