package com.iim.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iim.projet.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
