package com.example.MyCinemaApp.API;

import com.example.MyCinemaApp.dto.CinemaDto;
import com.example.MyCinemaApp.dto.CinemaNameDto;
import jakarta.persistence.Column;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ParserJSON {

    public static List<CinemaNameDto> parse(String response) throws ParseException {
        List<CinemaNameDto> cinemaList = new ArrayList<>();
        JSONObject object = (JSONObject) new JSONParser().parse(response);
        Iterator resultSetIterator = ((JSONArray) object.get("docs")).iterator();

        while(resultSetIterator.hasNext()){
            CinemaNameDto cinemaNameDto = new CinemaNameDto();
            JSONObject item =(JSONObject) resultSetIterator.next();


            cinemaNameDto.setName((String) item.get("name"));
            cinemaNameDto.setEnName((String) item.get("enName"));
            cinemaNameDto.setYear((Long) item.get("year"));
            cinemaNameDto.setId((Long) item.get("id"));
            cinemaNameDto.setDescription((String) item.get("description"));
            //cinemaNameDto.setInternalRating((Double)item.get("internalRating"));

            Iterator countries = ((JSONArray) item.get("countries")).iterator();
            if(countries.hasNext()){
                JSONObject country = (JSONObject) countries.next();
                cinemaNameDto.setCountry((String) country.get("name"));
            }

            Iterator genres = ((JSONArray) item.get("genres")).iterator();
            List<String> genresList = new ArrayList<>();
            while (genres.hasNext()){
                JSONObject genre = (JSONObject) genres.next();
                genresList.add((String) genre.get("name"));
            }
            cinemaNameDto.setGenres(genresList);

            JSONObject poster = (JSONObject)item.get("poster");
            cinemaNameDto.setPosterPhoto((String) poster.get("url"));

            JSONObject rating = (JSONObject)item.get("rating");
            cinemaNameDto.setInternalRating(Double.parseDouble(rating.get("kp").toString()));

            cinemaList.add(cinemaNameDto);
        }

        return cinemaList;
    }

    public static List<CinemaNameDto> parseOneFilm(String response) throws ParseException {
        System.out.println(response);
        List<CinemaNameDto> cinemaList = new ArrayList<>();
        JSONObject item = (JSONObject) new JSONParser().parse(response);

            CinemaNameDto cinemaNameDto = new CinemaNameDto();

            cinemaNameDto.setName((String) item.get("name"));
            if(item.get("enName") != null) {
                cinemaNameDto.setEnName((String) item.get("enName"));
            }
            if(item.get("year") != null) {
                cinemaNameDto.setYear((Long) item.get("year"));
            }
            if(item.get("id") != null) {
                cinemaNameDto.setId((Long) item.get("id"));
            }
            if(item.get("description") != null) {
                cinemaNameDto.setDescription((String) item.get("description"));
            }
            if(item.get("poster") != null) {
                JSONObject poster = (JSONObject) item.get("poster");
                cinemaNameDto.setPosterPhoto((String) poster.get("url"));
            }
            if(item.get("rating") != null) {
                JSONObject rating = (JSONObject) item.get("rating");
                cinemaNameDto.setInternalRating(Double.parseDouble(rating.get("kp").toString()));
            }
            cinemaList.add(cinemaNameDto);

        return cinemaList;
    }
}
