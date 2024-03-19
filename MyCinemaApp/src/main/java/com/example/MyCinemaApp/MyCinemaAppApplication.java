package com.example.MyCinemaApp;

import com.example.MyCinemaApp.entity.Cinema;
import com.example.MyCinemaApp.repositories.CinemaRepository;
import com.example.MyCinemaApp.services.CinemaService;
import com.example.MyCinemaApp.services.GenreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class MyCinemaAppApplication {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		SpringApplication.run(MyCinemaAppApplication.class, args);


	}

}
