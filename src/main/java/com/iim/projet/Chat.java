package com.iim.projet;

import jakarta.persistence.Entity;

@Entity
public class Chat extends Animal {

    protected Chat() {
    }

    public Chat(String name, int speed) {
        super(name, speed);
    }

    @Override
    public String getEspece() {
        return "chat";
    }
}
