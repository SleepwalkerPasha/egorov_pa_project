package ru.tinkoff.edu;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {ScrapperApplication.class,
    IntegrationEnvironment.IntegrationEnvironmentConfiguration.class})
public class JdbcLinkTest extends DatabaseIntegrationTest {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private TgChatRepository chatRepository;

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("app.databaseAccessType", () -> "jdbc");
    }

    @SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:MultipleStringLiterals"}) @Transactional
    @Rollback
    @Test
    void addTest() {
        long tgId = 1234567L;
        Link link = new Link();
        link.setUrl(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        link.setTgId(tgId);
        link.setCheckedAt(OffsetDateTime.now());
        link.setUpdatedAt(OffsetDateTime.now());

        Long addedTgChatId = chatRepository.add(tgId);

        Link newLink = linkRepository.add(link);

        assertNotNull(addedTgChatId);
        assertEquals(tgId, addedTgChatId);

        assertNotNull(newLink);
        assertEquals(link.getTgId(), newLink.getTgId());
        assertEquals(link.getUrl(), newLink.getUrl());
    }

    @SuppressWarnings("checkstyle:MagicNumber") @Transactional
    @Rollback
    @Test
    void removeTest() {
        long tgId = 12345678L;
        Link link = new Link();
        link.setUrl(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        link.setTgId(tgId);
        link.setCheckedAt(OffsetDateTime.now());
        link.setUpdatedAt(OffsetDateTime.now());

        Long addedTgChatId = chatRepository.add(tgId);
        Link newLink = linkRepository.add(link);

        Link removedLink = linkRepository.remove(link);

        Optional<Link> nullableLink = linkRepository.getLink(link);

        assertTrue(nullableLink.isEmpty());
    }

    @SuppressWarnings("checkstyle:MagicNumber") @Transactional
    @Rollback
    @Test
    void findAllTest() {
        long tgId = 123456789L;
        Link link = new Link();
        link.setUrl(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        link.setTgId(tgId);
        link.setCheckedAt(OffsetDateTime.now());
        link.setUpdatedAt(OffsetDateTime.now());

        Long addedTgChatId = chatRepository.add(tgId);
        Link newLink = linkRepository.add(link);

        Collection<Long> chatIds = chatRepository.findAll();
        Collection<Link> links = linkRepository.findAll();

        assertNotNull(chatIds);
        assertNotNull(links);
        assertEquals(1, chatIds.size());
        assertEquals(1, links.size());
    }

    @SuppressWarnings("checkstyle:MagicNumber") @Transactional
    @Rollback
    @Test
    void findAllLinksByTgIdTest() {
        long tgId = 12345679L;
        Link link = new Link();
        link.setUrl(URI.create("https://github.com/sanyarnd/tinkoff-java-course-2022/"));
        link.setTgId(tgId);
        link.setCheckedAt(OffsetDateTime.now());
        link.setUpdatedAt(OffsetDateTime.now());

        Long addedTgChatId = chatRepository.add(tgId);
        Link newLink = linkRepository.add(link);

        Collection<Link> linksById = linkRepository.findAllLinksById(addedTgChatId);

        assertNotNull(linksById);
        assertEquals(1, linksById.size());
    }
}
