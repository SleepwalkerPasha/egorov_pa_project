import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqTgChatRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = {ScrapperApplication.class, IntegrationEnvironment.IntegrationEnvironmentConfiguration.class})
public class JooqLinkTest extends DatabaseIntegrationTest {

    @Autowired
    private JooqLinkRepository linkRepository;

    @Autowired
    private JooqTgChatRepository chatRepository;

    @Transactional
    @Rollback
    @SneakyThrows
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

    @Transactional
    @Rollback
    @Test
    @SneakyThrows
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

    @Transactional
    @Rollback
    @Test
    @SneakyThrows
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

    @Transactional
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
