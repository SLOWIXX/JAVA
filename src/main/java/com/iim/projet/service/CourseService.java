package com.iim.projet.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.iim.projet.Animal;
import com.iim.projet.Chat;
import com.iim.projet.CourseResultat;
import com.iim.projet.Dog;
import com.iim.projet.ResultatCoureur;
import com.iim.projet.repository.ChatRepository;
import com.iim.projet.repository.DogRepository;

@Service
public class CourseService {

    static final int ARRIVEE = 100;

    private final ChatRepository chatRepository;
    private final DogRepository dogRepository;

    public CourseService(ChatRepository chatRepository, DogRepository dogRepository) {
        this.chatRepository = chatRepository;
        this.dogRepository = dogRepository;
    }

    public CourseResultat lancerCourse(Long chatId, Long chienId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Aucun chat avec l'id " + chatId));
        Dog chien = dogRepository.findById(chienId)
                .orElseThrow(() -> new IllegalArgumentException("Aucun chien avec l'id " + chienId));

        chat.courir();
        chien.courir();

        System.out.println();
        System.out.println("La course est lancée ! Premier arrivé à " + ARRIVEE + " mètres.");

        int distanceChat = 0;
        int distanceChien = 0;
        int tour = 0;

        while (distanceChat < ARRIVEE && distanceChien < ARRIVEE) {
            tour++;
            distanceChat += chat.getSpeed();
            distanceChien += chien.getSpeed();

            System.out.println();
            System.out.println(" Tour " + tour);
            System.out.println(chat.getName() + " : " + distanceChat + " m");
            System.out.println(chien.getName() + " : " + distanceChien + " m");
        }

        boolean egaliteParfaite = distanceChat == distanceChien;
        String gagnant = null;

        System.out.println();
        if (egaliteParfaite) {
            System.out.println("Égalité parfaite ! Ils franchissent la ligne ensemble au tour " + tour + ".");
        } else if (distanceChat > distanceChien) {
            chat.enregistrerVictoire();
            gagnant = chat.getName();
            chatRepository.save(chat);
            System.out.println(gagnant + " gagne la course en " + tour + " tours !");
        } else {
            chien.enregistrerVictoire();
            gagnant = chien.getName();
            dogRepository.save(chien);
            System.out.println(gagnant + " gagne la course en " + tour + " tours !");
        }

        List<ResultatCoureur> resultats = new ArrayList<>();
        resultats.add(new ResultatCoureur(chat.getName(), chat.getEspece(), chat.getSpeed(), distanceChat));
        resultats.add(new ResultatCoureur(chien.getName(), chien.getEspece(), chien.getSpeed(), distanceChien));

        return new CourseResultat(resultats, tour, egaliteParfaite, gagnant);
    }

    public List<Animal> classement() {
        List<Animal> classement = new ArrayList<>();
        classement.addAll(chatRepository.findAll());
        classement.addAll(dogRepository.findAll());
        classement.sort(Comparator.comparingInt(Animal::getVictoires).reversed());
        return classement;
    }
}
