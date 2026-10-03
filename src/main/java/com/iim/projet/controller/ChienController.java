package com.iim.projet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iim.projet.AnimalRequest;
import com.iim.projet.Dog;
import com.iim.projet.repository.DogRepository;

@RestController
@RequestMapping("/chien")
public class ChienController {

    private final DogRepository dogRepository;

    public ChienController(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @PostMapping
    public Dog creer(@RequestBody AnimalRequest requete) {
        Dog chien = new Dog(requete.nom(), requete.vitesse());
        return dogRepository.save(chien);
    }

    @GetMapping
    public List<Dog> lister() {
        return dogRepository.findAll();
    }
}
