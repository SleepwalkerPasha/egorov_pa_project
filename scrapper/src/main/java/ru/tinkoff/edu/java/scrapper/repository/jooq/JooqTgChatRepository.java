package ru.tinkoff.edu.java.scrapper.repository.jooq;

import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.CHAT;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINK;


@RequiredArgsConstructor
public class JooqTgChatRepository implements TgChatRepository {

    private final DSLContext jooq;

    @Override
    public long add(long chatId) {
        if (findByTgChatId(chatId).isPresent()) {
            throw new BadRequestException("данный пользователь уже зарегистрирован");
        }

        jooq.insertInto(CHAT, CHAT.ID)
                .values(chatId)
                .execute();
        return chatId;
    }

    @Override
    public long remove(long chatId) {
        if (findByTgChatId(chatId).isEmpty()) {
            throw new NotFoundException("данный пользователь не зарегистрирован");
        }
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
                .map(record1 -> {
                    ChatRecord chatRecord = (ChatRecord) record1.get(0);
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
                .map(record1 -> {
                    ChatRecord chatRecord = (ChatRecord) record1.get(0);
                    return chatRecord.get(CHAT.ID);
                });
    }

    @Override
    public Optional<Long> findByTgChatId(long id) {
        Record one = jooq.select()
                .from(CHAT)
                .where(CHAT.ID.eq(id))
                .fetchOne();
        if (one == null) {
            return Optional.empty();
        } else {
            return Optional.of(one.map(record1 -> {
                ChatRecord chatRecord = (ChatRecord) record1.get(0);
                return chatRecord.get(CHAT.ID);
            }));
        }
    }

}
