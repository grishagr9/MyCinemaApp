package com.example.MyCinemaApp.API;

import com.example.MyCinemaApp.config.DataAPI;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Component
public class CinemaAPI {

    private static int countPageRating = 1;

    /**
     * Получение случайного тайтла из базы.
     * @return JSON формат тайтла
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public static String getRandomFilm() throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String requestToServer = "https://api.kinopoisk.dev/v1.3/movie/random";
        //todo можно ли несколько

        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", DataAPI.API_KEY)
                .execute();

        String data = response.get().getResponseBody();
        client.close();

        System.out.println(data);

        return data;
    }

    /**
     * Получение тайтла по названию
     * @param name название фильма/мультика/сериала
     * @return JSON строку со списком подходящих фильмов
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public static String getFilmByName(String name) throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String encodedString = URLEncoder.encode(name, "UTF-8");
        //для получения большего количества результатов изменить лимит (max 250)
        String requestToServer = "https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=10&query=" + encodedString;


        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", DataAPI.API_KEY)
                .execute();

        String data = response.get().getResponseBody();
        client.close();

       // System.out.println(data);

        return data;
    }

    /**
     * Поиск тайтлов по промежутку рейтинга
     * @param start от какой оценки начинать поиск
     * @param end до какой оценки искать
     * @return JSON строка со списком подходящих фильмов
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String getFilmByRating(int start, int end) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String request = start + "-" + end;
        //для получения большего количества результатов изменить лимит (max 250)
        String requestToServer = "https://api.kinopoisk.dev/v1.4/movie?page="+countPageRating+"&limit=10&rating.kp=" + request;
        countPageRating++;
        if(countPageRating >= 10){
            countPageRating = 1;
        }

        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", DataAPI.API_KEY)
                .execute();

        String data = response.get().getResponseBody();
        client.close();

        System.out.println(data);

        return data;
    }

    /**
     * Поиск фильма по рейтингу
     * @param start минимальный рейтинг
     * @return JSON строка со списком подходящих фильмов
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String getFilmByRating(int start) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String request = start + "-" + 10;
        //для получения большего количества результатов изменить лимит (max 250)
        String requestToServer = "https://api.kinopoisk.dev/v1.4/movie?page=" + countPageRating + "&limit=10&rating.kp=" + request;
        countPageRating++;
        if(countPageRating >= 10){
            countPageRating = 1;
        }

        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", DataAPI.API_KEY)
                .execute();

        String data = response.get().getResponseBody();
        client.close();

        System.out.println(data);

        return data;
    }

    /**
     * Поиск фильма по жанру
     * @param genre жанр
     * @return список подходящих фильмов
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String getFilmByGenre(String genre) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        String encodedString = URLEncoder.encode(genre, "UTF-8");
        //для получения большего количества результатов изменить лимит (max 250)
        String requestToServer = "https://api.kinopoisk.dev/v1.4/movie?page=1&limit=20&selectFields=&genres.name=" + encodedString;


        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", DataAPI.API_KEY)
                .execute();

        String data = response.get().getResponseBody();
        client.close();

        // System.out.println(data);

        return data;
    }

    /**
     * поиск фильмов нынешнего года, получаем рандомную сраницу поиска с 10 фильмами
     * @return список подходящих фильмов
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public static String getRecentFilm() throws ExecutionException, InterruptedException, IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        //для получения большего количества результатов изменить лимит (max 250)
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Random rand = new Random();
        int page = rand.nextInt(10) + 1;

        String requestToServer = "https://api.kinopoisk.dev/v1.4/movie?page=" + page + "&limit=10&year=" + year;

        var response = client.prepare("GET", requestToServer)
                .setHeader("X-API-KEY", DataAPI.API_KEY)
                .execute();

        String data = response.get().getResponseBody();
        client.close();

        return data;
    }
}
