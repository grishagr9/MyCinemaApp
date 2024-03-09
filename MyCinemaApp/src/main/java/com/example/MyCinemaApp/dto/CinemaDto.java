package com.example.MyCinemaApp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CinemaDto {

    private String title;

    @NonNull
    private String id;

    private Boolean isSeries;

    private Long releaseYear;

    private String photo;

}
