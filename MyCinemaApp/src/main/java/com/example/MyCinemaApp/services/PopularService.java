package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.API.CinemaAPI;
import com.example.MyCinemaApp.API.ParserJSON;
import com.example.MyCinemaApp.data.Data;
import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.dto.GenresDto;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PopularService {


    public List<CinemaNameDto> getCinemaByRating(int i) {
        if(Data.popular == null) {
            List<CinemaNameDto> result = new ArrayList<>();
            try {
                result = ParserJSON.parse(CinemaAPI.getFilmByRating(i));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Data.popular = result;
            return result;
        }else return Data.popular;
    }
}
