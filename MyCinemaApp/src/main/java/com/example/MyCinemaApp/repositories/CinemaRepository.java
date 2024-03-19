package com.example.MyCinemaApp.repositories;

import com.example.MyCinemaApp.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
