package com.example.MyCinemaApp.data;

import com.example.MyCinemaApp.dto.CinemaNameDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Data {
    public static List<CinemaNameDto> popular;

    public static Map<String, List<CinemaNameDto>> cinemaGenre;


}
