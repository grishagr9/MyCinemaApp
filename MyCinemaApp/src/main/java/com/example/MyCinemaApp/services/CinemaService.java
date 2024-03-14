package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.API.CinemaAPI;
import com.example.MyCinemaApp.API.ParserJSON;
import com.example.MyCinemaApp.dto.CinemaNameDto;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CinemaService {

    public List<CinemaNameDto> getFilmsByName(String name) {
        if(name == null){
            return null;
        }

        String responseFromAPI = null;
        try {
            responseFromAPI = CinemaAPI.getFilmByName(name);
        } catch (ExecutionException e) {
            System.err.println(e.getMessage() + " (ExecutionException) " + e.getCause());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + " (InterruptedException) " + e.getCause());
        } catch (IOException e) {
            System.err.println(e.getMessage() + " (IOException) " + e.getCause());;
        }

        List<CinemaNameDto> result = null;
        try {
            result = ParserJSON.parse(responseFromAPI);
        } catch (ParseException e) {
            System.err.println("ParseException");
        }

        return result;
    }

    public List<CinemaNameDto> getFilmsByGenre(String genre) {
        if(genre == null){
            return null;
        }

        String responseFromAPI = null;
        try {
            responseFromAPI = CinemaAPI.getFilmByGenre(genre);
        } catch (ExecutionException e) {
            System.err.println(e.getMessage() + " (ExecutionException) " + e.getCause());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + " (InterruptedException) " + e.getCause());
        } catch (IOException e) {
            System.err.println(e.getMessage() + " (IOException) " + e.getCause());;
        }

        List<CinemaNameDto> result = null;
        try {
            result = ParserJSON.parse(responseFromAPI);
        } catch (ParseException e) {
            System.err.println("ParseException");
        }

        return result;
    }

    public List<CinemaNameDto> getFilmByRating(int start, int end){
        if(start < 0 || end < 0 || end < start || start > 10){
            return null;
        }

        String responseFromAPI = null;
        try {
            responseFromAPI = CinemaAPI.getFilmByRating(end, start);
        } catch (ExecutionException e) {
            System.err.println(e.getMessage() + " (ExecutionException) " + e.getCause());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + " (InterruptedException) " + e.getCause());
        } catch (IOException e) {
            System.err.println(e.getMessage() + " (IOException) " + e.getCause());;
        }

        List<CinemaNameDto> result = null;
        try {
            result = ParserJSON.parse(responseFromAPI);
        } catch (ParseException e) {
            System.err.println("ParseException");
        }

        return result;
    }

    public List<CinemaNameDto> getFilmByRating(int start){
        if(start < 0 || start > 10){
            return null;
        }

        String responseFromAPI = null;
        try {
            responseFromAPI = CinemaAPI.getFilmByRating(start);
        } catch (ExecutionException e) {
            System.err.println(e.getMessage() + " (ExecutionException) " + e.getCause());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + " (InterruptedException) " + e.getCause());
        } catch (IOException e) {
            System.err.println(e.getMessage() + " (IOException) " + e.getCause());;
        }

        List<CinemaNameDto> result = null;
        try {
            result = ParserJSON.parse(responseFromAPI);
        } catch (ParseException e) {
            System.err.println("ParseException");
        }

        return result;
    }

    public CinemaNameDto getRandomFilm(){
        String responseFromAPI = null;
        try {
            responseFromAPI = CinemaAPI.getRandomFilm();
        } catch (ExecutionException e) {
            System.err.println(e.getMessage() + " (ExecutionException) " + e.getCause());
        } catch (InterruptedException e) {
            System.err.println(e.getMessage() + " (InterruptedException) " + e.getCause());
        } catch (IOException e) {
            System.err.println(e.getMessage() + " (IOException) " + e.getCause());;
        }

        CinemaNameDto result = null;
        try {
            result = ParserJSON.parseOneFilm(responseFromAPI).get(0);
        } catch (ParseException e) {
            System.err.println("ParseException");
        }

        return result;
    }
}
