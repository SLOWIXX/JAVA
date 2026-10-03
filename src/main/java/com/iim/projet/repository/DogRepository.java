package com.iim.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iim.projet.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}
