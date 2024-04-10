package com.example.MyCinemaApp.controllers;

import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.services.CinemaService;
import com.example.MyCinemaApp.services.FavoriteService;
import com.example.MyCinemaApp.services.MainPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FavoritePageController {

    final private FavoriteService favoriteService;
    final private MainPageService mainPageService;

    @ModelAttribute("filmRecent")
    public List<CinemaNameDto> filmRecent() throws IOException, ParseException, ExecutionException, InterruptedException {
        return mainPageService.getAllFilm();
    }
    @ModelAttribute("favoriteCinema")
    public List<CinemaNameDto> favoriteCinema(){
        return favoriteService.getAllCinemas();
    }

    @GetMapping("/favorite")
    public String favoritePage(Model model){
        model.addAttribute("favoriteCinema", favoriteCinema());
        return "favorite";
    }

    @GetMapping("/favorite/{nameCinema}")
    public String addCinema(@PathVariable(value = "nameCinema",required = false)Long cinemaId,
                            Model model)  {
        model.addAttribute("favoriteCinema", favoriteCinema());
        if(favoriteService.containsCinema(cinemaId)){
            return "redirect:/favorite";
        }
        favoriteService.addFavorite(cinemaId);
        return "redirect:/favorite";
    }
}
