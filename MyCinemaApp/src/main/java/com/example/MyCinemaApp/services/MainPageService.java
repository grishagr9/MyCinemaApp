package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.API.CinemaAPI;
import com.example.MyCinemaApp.API.ParserJSON;
import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.entity.Cinema;
import com.example.MyCinemaApp.entity.Genre;
import com.example.MyCinemaApp.repositories.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final CinemaRepository cinemaRepository;
    private final CinemaService cinemaService;

    private void addRecentFilms() throws ParseException, IOException, ExecutionException, InterruptedException {
        var response = CinemaAPI.getRecentFilm();
        var parseResponse = ParserJSON.parse(response);

        for(CinemaNameDto cinemaNameDto: parseResponse){
            Cinema cinema = new Cinema();
            cinema.setName(cinemaNameDto.getName());
            cinema.setKp_id(cinemaNameDto.getId());
            if(cinemaNameDto.getDescription().length() <= 255){
                cinema.setDescription(cinemaNameDto.getDescription());
            }else {
                cinema.setDescription(cinemaNameDto.getDescription().substring(0, 254));
            }
            cinema.setInternalRating(cinemaNameDto.getInternalRating());
            cinema.setPosterPhoto(cinemaNameDto.getPosterPhoto());
            //cinema.setGenreSet(cinemaNameDto.getGenres().stream().map(x -> new Genre(x)));

            cinemaRepository.save(cinema);
        }
    }

    public void fillRecentFilm(){
        try {
            addRecentFilms();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CinemaNameDto> getAllFilm(){
        return cinemaService.toDtos(cinemaRepository.findAll().stream().limit(10).toList());
    }
}
