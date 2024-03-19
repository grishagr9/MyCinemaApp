package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.entity.Genre;
import com.example.MyCinemaApp.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public void add(String name){
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.save(genre);
    }
}
