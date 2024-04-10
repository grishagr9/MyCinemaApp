package com.example.MyCinemaApp.data;

import com.example.MyCinemaApp.dto.CinemaNameDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Data {
    public static List<CinemaNameDto> popular;

    public static Map<String, List<CinemaNameDto>> cinemaGenre;

    public static List<String> genres = new ArrayList<>(List.of(
            "аниме",
            "биография",
            "боевик",
            "драма",
            "комедия",
            "ужасы",
            "фантастика"
    ));
}
