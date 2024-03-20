package com.example.MyCinemaApp.entity.links;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cinema2genre")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cinema2Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cinema_id")
    private Long cinemaId;

    @Column(name = "genre_id")
    private Long genreId;

}
