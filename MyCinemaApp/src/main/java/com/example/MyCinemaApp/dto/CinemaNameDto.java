package com.example.MyCinemaApp.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaNameDto {

    private String name;

    private String enName;

    private Long year;

    private List<String> genres;

    private Long id;

    private String description;

    private String country;

    private Double internalRating;

    private String posterPhoto;

    @Override
    public String toString() {
        return String.format("Название: %s, Международное название:%s\n" +
                "Год выпуска: %d\n" +
                "Жанр: %s\n" +
                "Рейтинг: %f\n"+
                "ID: %d \n" +
                "Страна: %s\n" +
                "Описание: %s\n" +
                "Фото %s\n",name,enName,year,genres.toString(),internalRating,id, country, description, posterPhoto);
    }
}
