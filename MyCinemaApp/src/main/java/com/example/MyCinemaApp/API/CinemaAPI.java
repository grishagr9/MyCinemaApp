package com.example.MyCinemaApp.API;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class CinemaAPI {


    public static String getRandomFilm() throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String requestToServer = "https://api.kinopoisk.dev/v1.3/movie/random";


        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", "1JA084P-96XMR59-HC5RND9-KT65KVE")
                .execute();

        String data = response.get().getResponseBody();
        client.close();

        System.out.println(data);

        return data;
    }

    public static String getFilmByName(String name) throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String encodedString = URLEncoder.encode(name, "UTF-8");
        String requestToServer = "https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=10&query=" + encodedString;


        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", "1JA084P-96XMR59-HC5RND9-KT65KVE")
                .execute();

        String data = response.get().getResponseBody();
        client.close();

       // System.out.println(data);

        return data;
    }

    public static String getFilmByRating(int start, int end) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String request = start + "-" + end;
        String requestToServer = "https://api.kinopoisk.dev/v1.4/movie?page=1&limit=10&rating.kp=" + request;


        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", "1JA084P-96XMR59-HC5RND9-KT65KVE")
                .execute();

        String data = response.get().getResponseBody();
        client.close();

        System.out.println(data);

        return data;
    }

    public static String getFilmByRating(int start) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String request = start + "-" + 10;
        String requestToServer = "https://api.kinopoisk.dev/v1.4/movie?page=1&limit=10&rating.kp=" + request;


        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", "1JA084P-96XMR59-HC5RND9-KT65KVE")
                .execute();

        String data = response.get().getResponseBody();
        client.close();

        System.out.println(data);

        return data;
    }
}
