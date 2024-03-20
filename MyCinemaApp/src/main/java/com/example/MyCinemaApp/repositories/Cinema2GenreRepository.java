package com.example.MyCinemaApp.repositories;

import com.example.MyCinemaApp.entity.links.Cinema2Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Cinema2GenreRepository extends JpaRepository<Cinema2Genre, Long> {

    List<Cinema2Genre> findAllByGenreId(Long genre_id);
}
