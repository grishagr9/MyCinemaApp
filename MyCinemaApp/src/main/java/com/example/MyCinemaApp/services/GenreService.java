package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.dto.GenresDto;
import com.example.MyCinemaApp.entity.Cinema;
import com.example.MyCinemaApp.entity.Genre;
import com.example.MyCinemaApp.entity.links.Cinema2Genre;
import com.example.MyCinemaApp.repositories.Cinema2GenreRepository;
import com.example.MyCinemaApp.repositories.CinemaRepository;
import com.example.MyCinemaApp.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final Cinema2GenreRepository cinema2GenreRepository;
    private final CinemaRepository cinemaRepository;

    public void add(String name){
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.save(genre);
    }

    public Map<Long, GenresDto> getAllGenres() {
        final List<Genre> genreList = genreRepository.findAll();
        return toDtoList(genreList);
    }

    private Map<Long, GenresDto> toDtoList(List<Genre> genreEntities) {
        final Map<Long, GenresDto> genresDto = new HashMap<>();
        for (Genre genre : genreEntities) {
            genresDto.put(genre.getId(), toDto(genre));
        }
        return genresDto;
    }

    private GenresDto toDto(Genre genreEntity){
        if(genreEntity != null){
            GenresDto genresDto = new GenresDto();
            genresDto.setName(genreEntity.getName());
            return genresDto;
        }
        else{
            return new GenresDto();
        }
    }

    public Genre getGenreByName(String nameGenre) {
        Genre genre = genreRepository.findByName(nameGenre);
        return genre == null ? new Genre() : genre;
    }

    public List<Cinema> getAllCinemaByNameGenre(String genre){
        Long genreId = getIdByGenre(genre);
        List<Cinema2Genre> cinemasId = cinema2GenreRepository.findAllByGenreId(genreId);
        List<Cinema> cinemas = new ArrayList<>();
        for (Cinema2Genre id: cinemasId) {
            cinemas.add(cinemaRepository.findCinemaById(id.getCinemaId()));
        }

        return cinemas;
    }

    private Long getIdByGenre(String genre){
        Genre res = genreRepository.findByName(genre);
        Logger.getLogger(GenreService.class.getSimpleName()).info(genre + " id = " + res);

        return res == null ? 0 : res.getId();
    }
}
