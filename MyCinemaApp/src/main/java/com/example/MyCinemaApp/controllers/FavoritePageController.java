package com.example.MyCinemaApp.controllers;

import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.services.CinemaService;
import com.example.MyCinemaApp.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FavoritePageController {

    final private FavoriteService favoriteService;

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
    public String addCinema(@PathVariable(value = "nameCinema",required = false)CinemaNameDto nameCinema,
                            Model model){
        favoriteService.addFavorite(nameCinema);
        return "favorite";
    }
}
