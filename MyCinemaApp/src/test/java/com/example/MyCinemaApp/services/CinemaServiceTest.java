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
    void getResponse() throws IOException, ExecutionException, InterruptedException, ParseException {
        String fileName = "C:\\Users\\Grisha\\IdeaProjects\\MyCinemaApp\\MyCinemaApp\\src\\test\\java\\com\\example\\MyCinemaApp\\services\\a.txt";

        // Чтение всего файла в одну строку
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(content);

        System.out.println(ParserJSON.parse(content));
    }

    @Test
    void testRequestsByNameEng() throws IOException, ExecutionException, InterruptedException, ParseException {
        String name = "Godfather";
        var response = CinemaAPI.getFilmByName(name);
        var parseResponse = ParserJSON.parse(response);

        System.out.println(parseResponse);
    }

    @Test
    void testRequestsByNameRus() throws IOException, ExecutionException, InterruptedException, ParseException {
        String name = "Человек паук";
        var response = CinemaAPI.getFilmByName(name);
        var parseResponse = ParserJSON.parse(response);

        System.out.println(parseResponse);
    }

    @Test
    void testRequestsByRating() throws IOException, ExecutionException, InterruptedException, ParseException {
        var response = CinemaAPI.getFilmByRating(8,10);
        var parseResponse = ParserJSON.parse(response);

        System.out.println(parseResponse);
    }

    @Test
    void testRequestsRandom() throws IOException, ExecutionException, InterruptedException, ParseException {
        String name = "Godfather";
        var response = CinemaAPI.getFilmByName(name);
        var parseResponse = ParserJSON.parse(response);

        System.out.println(parseResponse);
    }
}

