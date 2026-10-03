package com.iim.projet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iim.projet.AnimalRequest;
import com.iim.projet.Chat;
import com.iim.projet.repository.ChatRepository;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatRepository chatRepository;

    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @PostMapping
    public Chat creer(@RequestBody AnimalRequest requete) {
        Chat chat = new Chat(requete.nom(), requete.vitesse());
        return chatRepository.save(chat);
    }

    @GetMapping
    public List<Chat> lister() {
        return chatRepository.findAll();
    }
}
