package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.dto.CinemaDto;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CinemaService {


    public static String getResponse(String request) throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String requestToServer = "https://moviesdatabase.p.rapidapi.com/titles?" + request;


        var response = client.prepare("GET", requestToServer)
                .setHeader("X-RapidAPI-Key", "e162691449mshfb64da0bbea51c2p115b56jsn6f8fffaaab34")
                .setHeader("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
                .execute();

        String data = response.get().getResponseBody();
        client.close();

        System.out.println(data);

        return data;
    }

    /**
     * a function for processing the json format into a list of movie objects
     * @param response a string in the JSON format of the server response
     * @return List of movie objects
     * @throws ParseException
     */
    public List<CinemaDto> parsingResponse(String response) throws ParseException {
        List<CinemaDto> cinemaList = new ArrayList<>();
        JSONObject object = (JSONObject) new JSONParser().parse(response);
        Iterator resultSetIterator = ((JSONArray) object.get("results")).iterator();

        while(resultSetIterator.hasNext()){
            CinemaDto cinemaDto = new CinemaDto();
            JSONObject item =(JSONObject) resultSetIterator.next();
            JSONObject titleText = (JSONObject) item.get("titleText");
            JSONObject titleType = (JSONObject) item.get("titleType");
            JSONObject releaseYear = (JSONObject) item.get("releaseYear");
            JSONObject primaryImage = (JSONObject) item.get("primaryImage");

            cinemaDto.setId( (String) item.get("id"));
            if(primaryImage != null) {
                cinemaDto.setPhoto((String) primaryImage.get("url"));
            }
            if(titleText != null) {
                cinemaDto.setTitle((String) titleText.get("text"));
            }
            if(titleType != null) {
                cinemaDto.setIsSeries((Boolean) titleType.get("isSeries"));
            }
            if(releaseYear != null) {
                cinemaDto.setReleaseYear((Long) releaseYear.get("year"));
            }

            cinemaList.add(cinemaDto);
        }

        return cinemaList;
    }

    public static String getResponse() throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String request = "https://moviesdatabase.p.rapidapi.com/titles?genre=Drama";

        var response = client.prepare("GET", request)
                .setHeader("X-RapidAPI-Key", "e162691449mshfb64da0bbea51c2p115b56jsn6f8fffaaab34")
                .setHeader("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
                .execute();
//                .toCompletableFuture()
//                .thenAccept(System.out::println)
//                .join();
        String data = response.get().getResponseBody();
        client.close();

        System.out.println(data);

        return data;
    }
}
