package com.iim.projet;

import jakarta.persistence.Entity;

@Entity
public class Dog extends Animal {

    protected Dog() {
    }

    public Dog(String name, int speed) {
        super(name, speed);
    }

    @Override
    public String getEspece() {
        return "chien";
    }
}
