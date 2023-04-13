package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;

@RestController
public class GithubController {

    @PostMapping("/tg-chat/{id}")
    public void registerNewChat(@PathVariable Long id) {
        if (id != 400)
            throw new BadRequestException("id ==" + id);
        if (id == 404)
            throw new NotFoundException("id is not found");
    }

    @DeleteMapping("/tg-chat/{id}")
    public void deleteChat(@PathVariable Long id) {
        if (id != 400)
            throw new BadRequestException("id ==" + id);
        if (id == 404)
            throw new NotFoundException("id is not found");
    }

}
