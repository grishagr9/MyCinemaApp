package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.API.CinemaAPI;
import com.example.MyCinemaApp.API.ParserJSON;
import com.example.MyCinemaApp.data.Data;
import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.dto.GenresDto;
import com.example.MyCinemaApp.entity.Cinema;
import com.example.MyCinemaApp.entity.Genre;
import com.example.MyCinemaApp.entity.links.Cinema2Genre;
import com.example.MyCinemaApp.repositories.Cinema2GenreRepository;
import com.example.MyCinemaApp.repositories.CinemaRepository;
import com.example.MyCinemaApp.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final Cinema2GenreRepository cinema2GenreRepository;
    private final CinemaRepository cinemaRepository;

    private static long id = 1;

    public void add(String name){
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.save(genre);
    }

    public Map<Long, GenresDto> getAllGenres() {
        final List<Genre> genreList = genreRepository.findAll();
        if(genreList.isEmpty()){
            for(String nameGenre: Data.genres){
                genreList.add(toGenre(nameGenre));
            }
        }
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

    private Genre toGenre(String genreName){
        if(genreName != null){
            Genre genre = new Genre();
            genre.setName(genreName);
            genre.setId(id++);
            return genre;
        }
        else{
            return new Genre();
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

    public List<CinemaNameDto> getFilmByGenreName(String name){
        List<CinemaNameDto> res = new ArrayList<>();
        if(Data.cinemaGenre == null){
            Data.cinemaGenre = new HashMap<>();
        }
        if(Data.cinemaGenre.get(name) == null){
            try {
                res =  ParserJSON.parse(CinemaAPI.getFilmByGenre(name));
                Data.cinemaGenre.put(name, res);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{
            res = Data.cinemaGenre.get(name);
        }

        return res;
    }
}
