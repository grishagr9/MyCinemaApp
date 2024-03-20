package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.API.CinemaAPI;
import com.example.MyCinemaApp.API.ParserJSON;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CinemaServiceTest {

    @Test
    void givenFileWithJSON_whenParseJSON_thenReturnDTOClass() throws IOException, ExecutionException, InterruptedException, ParseException {
        //given
        String fileName = "C:\\Users\\Grisha\\IdeaProjects\\MyCinemaApp\\MyCinemaApp\\src\\test\\java\\com\\example\\MyCinemaApp\\services\\a.txt";
        String content = null;

        //when
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //then
        System.out.println(content);
        System.out.println(ParserJSON.parse(content));
    }

    @Test
    void givenEngName_whenParseFromAPI_thenReturnDTOClassObj() throws IOException, ExecutionException, InterruptedException, ParseException {
        //given
        String name = "Godfather";

        //when
        var response = CinemaAPI.getFilmByName(name);
        System.out.println(response);
        var parseResponse = ParserJSON.parse(response);

        //then
        System.out.println(parseResponse);
    }

    @Test
    void givenRusName_whenParseFromAPI_thenReturnDTOClassObj() throws IOException, ExecutionException, InterruptedException, ParseException {
        //given
        String name = "Человек паук";

        //when
        var response = CinemaAPI.getFilmByName(name);
        var parseResponse = ParserJSON.parse(response);

        //then
        System.out.println(parseResponse);
    }

    @Test
    void givenIntervalRating_whenParseFromAPI_thenReturnDTOClassObj() throws IOException, ExecutionException, InterruptedException, ParseException {
       //given
        int start = 8;
        int end = 10;

        //when
        var response = CinemaAPI.getFilmByRating(start, end);
        var parseResponse = ParserJSON.parse(response);

        //then
        System.out.println(parseResponse);
    }

    @Test
    void givenMinRating_whenParseFromAPI_thenReturnDTOClassObj() throws IOException, ExecutionException, InterruptedException, ParseException {
        //given
        int start = 7;

        //when
        var response = CinemaAPI.getFilmByRating(start);
        var parseResponse = ParserJSON.parse(response);

        //then
        System.out.println(parseResponse);
    }

    @Test
    void givenRandomFilm_whenParseFromAPI_thenReturnDTOClassObj() throws IOException, ExecutionException, InterruptedException, ParseException {
        //when
        var response = CinemaAPI.getRandomFilm();
        var parseResponse = ParserJSON.parseOneFilm(response);

        //then
        System.out.println(parseResponse);
    }

    @Test
    void givenGenreName_whenParseFromAPI_thenReturnDTOClassObj() throws IOException, ExecutionException, InterruptedException, ParseException {
        //given
        String genre = "криминал";

        //when
        var response = CinemaAPI.getFilmByGenre(genre);
        var parseResponse = ParserJSON.parse(response);

        //then
        System.out.println(parseResponse);
    }

    @Test
    void givenYear_whenParse_thenReturnNewCinema() throws IOException, ExecutionException, InterruptedException, ParseException {
        var response = CinemaAPI.getRecentFilm();
        var parse = ParserJSON.parse(response);

        System.out.println(parse);
    }
}

