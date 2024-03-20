package com.example.MyCinemaApp.controllers;

import com.example.MyCinemaApp.dto.GenresDto;
import com.example.MyCinemaApp.entity.Genre;
import com.example.MyCinemaApp.services.CinemaService;
import com.example.MyCinemaApp.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class GenrePageController {

    final private GenreService genreService;
    final private CinemaService cinemaService;

    @ModelAttribute("booksGenres")
    public Map<Long, GenresDto> booksGenres(){
        return genreService.getAllGenres();
    }

    @GetMapping("/genres")
    public String genresPage(Model model){
        model.addAttribute("booksGenres", booksGenres().values());
        return "genres";
    }

    @GetMapping(value ="/genres/{nameGenre}")
    public String getGenrePage(@PathVariable(value = "nameGenre",required = false)String nameGenre, Model model){
        Genre genre = genreService.getGenreByName(nameGenre);
        model.addAttribute("cinemaGenreResult", cinemaService.toDtos(genreService.getAllCinemaByNameGenre(nameGenre)));
        model.addAttribute("currentGenre",genre);

        return "genre_slug";
    }
}
