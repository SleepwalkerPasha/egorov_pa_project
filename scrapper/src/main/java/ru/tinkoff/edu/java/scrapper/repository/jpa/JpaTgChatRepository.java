package ru.tinkoff.edu.java.scrapper.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.interfaces.JpaChatDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Qualifier("JpaTgChatRepository")
@RequiredArgsConstructor
public class JpaTgChatRepository implements TgChatRepository {

    private final JpaChatDao jpaChatDao;

    @Override
    @Transactional
    public long add(long chatId) {
        if (jpaChatDao.findById(chatId).isPresent())
            throw new BadRequestException("данный пользователь уже зарегистрирован");
        else {
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setId(chatId);
            jpaChatDao.save(chatEntity);
            return chatId;
        }
    }

    @Override
    @Transactional
    public long remove(long chatId) {
        if (jpaChatDao.findById(chatId).isEmpty())
            throw new NotFoundException("данный пользователь не зарегистрирован");
        else {
            jpaChatDao.deleteById(chatId);
            return chatId;
        }
    }

    @Override
    public Collection<Long> findAll() {
        return jpaChatDao.findAll().stream().map(ChatEntity::getId).collect(Collectors.toList());
    }

    @Override
    public Collection<Long> findByLinkId(long id) {
        return new ArrayList<>(jpaChatDao.findAllByLinkId(id));
    }

    @Override
    public Optional<Long> findByTgChatId(long id) {
        return jpaChatDao.findById(id).map(ChatEntity::getId);
    }
}
