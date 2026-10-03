package com.iim.projet;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// @MappedSuperclass : Chat et Dog héritent de cette classe en Java (extends Animal),
// et cette annotation dit à Hibernate de faire pareil en base : les colonnes id/name/speed/victoires
// sont recopiées dans la table "chat" ET dans la table "dog". Animal lui-même n'a pas de table.
@MappedSuperclass
public abstract class Animal implements Coureur {

    // @Id : cette colonne est la clé primaire.
    // @GeneratedValue(IDENTITY) : la valeur n'est pas choisie par nous, Postgres l'incrémente
    // tout seul (1, 2, 3...) via une colonne auto-incrémentée, comme un id de ligne classique.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int speed;
    private int victoires;

    protected Animal() {
    }

    public Animal(String name, int speed) {
        this.name = name;
        this.speed = speed;
    }

    public abstract String getEspece();

    @Override
    public void courir() {
        System.out.println("Le " + getEspece() + " " + name + " court à la vitesse de " + speed);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public int getVictoires() {
        return victoires;
    }

    public void enregistrerVictoire() {
        this.victoires++;
    }
}
