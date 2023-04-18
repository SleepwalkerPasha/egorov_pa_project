package ru.tinkoff.edu.java.scrapper.repository.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat.CHAT;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link.LINK;


@Repository
@Qualifier("JooqTgChatRepository")
public class JooqTgChatRepository implements TgChatRepository {

    private final DSLContext jooq;

    @Autowired
    public JooqTgChatRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public long add(long chatId) {
        return Objects.requireNonNull(jooq.insertInto(CHAT, CHAT.ID)
                        .values(chatId)
                        .returningResult(CHAT.ID)
                        .fetchOne())
                .map(record -> {
                    ChatRecord chatRecord = (ChatRecord) record.get(0);
                    return chatRecord.get(CHAT.ID);
                });
    }

    @Override
    public long remove(long chatId) {
        jooq.deleteFrom(CHAT)
                .where(CHAT.ID.eq(chatId))
                .execute();
        return chatId;
    }

    @Override
    public Collection<Long> findAll() {
        return jooq.selectFrom(CHAT)
                .fetch()
                .map(record -> {
                    ChatRecord chatRecord = (ChatRecord) record.get(0);
                    return chatRecord.get(CHAT.ID);
                });
    }

    @Override
    public Collection<Long> findByLinkId(long id) {
        return jooq.select()
                .from(LINK)
                .leftJoin(CHAT)
                .on(CHAT.ID.eq(LINK.TG_ID))
                .where(LINK.ID.eq(id))
                .fetch()
                .map(record -> {
                    ChatRecord chatRecord = (ChatRecord) record.get(0);
                    return chatRecord.get(CHAT.ID);
                });
    }

    @Override
    public Optional<Long> findByTgChatId(long id) {
        return Optional.of(jooq.selectFrom(CHAT)
                .where(CHAT.ID.eq(id))
                .fetchOne()
                .map(record -> {
                    ChatRecord chatRecord = (ChatRecord) record.get(0);
                    return chatRecord.get(CHAT.ID);
                }));
    }

}
