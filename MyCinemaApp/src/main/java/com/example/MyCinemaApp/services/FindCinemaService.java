package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.API.CinemaAPI;
import com.example.MyCinemaApp.API.ParserJSON;
import com.example.MyCinemaApp.data.Data;
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
public class FindCinemaService {

    private final CinemaRepository cinemaRepository;

    public CinemaNameDto findCinemaByName(String name){
        var searchResultFromDataBase = cinemaRepository.findCinemaByName(name);
        if(searchResultFromDataBase != null){
            return toDTO(searchResultFromDataBase);
        }

        if(Data.popular != null) {
            CinemaNameDto searchFromCash = Data.popular.stream().
                    filter(x -> x.getName().equals(name)).
                    findAny().orElse(null);
            if (searchFromCash != null) {
                return searchFromCash;
            }
        }

        String response = null;
        List<CinemaNameDto> parseResult = null;
        try {
            response = CinemaAPI.getFilmByName(name);
        } catch (ExecutionException | InterruptedException | IOException e) {
            System.err.println(e.getMessage());
        }
        if(response != null){
            try {
                parseResult = ParserJSON.parse(response);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        if(parseResult != null && !parseResult.isEmpty()){
            return parseResult.get(0);
        }


        return new CinemaNameDto();
    }


    private CinemaNameDto toDTO(Cinema cinema) {
        CinemaNameDto result = new CinemaNameDto();

        result.setInternalRating(cinema.getInternalRating());
        result.setPosterPhoto(cinema.getPosterPhoto());
        result.setGenres(cinema.getGenreSet().stream().map(Genre::getName).toList());
        result.setDescription(cinema.getDescription());
        result.setName(cinema.getName());
        result.setId(cinema.getKp_id());

        return result;
    }
}
