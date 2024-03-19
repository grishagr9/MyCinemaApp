package com.example.MyCinemaApp.repositories;

import com.example.MyCinemaApp.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
