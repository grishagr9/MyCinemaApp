package com.example.MyCinemaApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "consumer")
@ToString
@NoArgsConstructor
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @ManyToMany(mappedBy = "userSet")
    private Set<Cinema> cinemaSet;
}
