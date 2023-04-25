package ru.tinkoff.edu.java.scrapper.repository.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.util.Collection;
import java.util.Optional;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.CHAT;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINK;


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
        if (findByTgChatId(chatId).isPresent())
            throw new BadRequestException("данный пользователь уже зарегистрирован");

        jooq.insertInto(CHAT, CHAT.ID)
                .values(chatId)
                .execute();
        return chatId;
    }

    @Override
    public long remove(long chatId) {
        if (findByTgChatId(chatId).isEmpty())
            throw new NotFoundException("данный пользователь не зарегистрирован");
        jooq.deleteFrom(CHAT)
                .where(CHAT.ID.eq(chatId))
                .execute();
        return chatId;
    }

    @Override
    public Collection<Long> findAll() {
        return jooq.select(CHAT)
                .from(CHAT)
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
        Record one = jooq.select()
                .from(CHAT)
                .where(CHAT.ID.eq(id))
                .fetchOne();
        if (one == null) return Optional.empty();
        else return Optional.of(one.map(record -> {
            ChatRecord chatRecord = (ChatRecord) record.get(0);
            return chatRecord.get(CHAT.ID);
        }));
    }

}
