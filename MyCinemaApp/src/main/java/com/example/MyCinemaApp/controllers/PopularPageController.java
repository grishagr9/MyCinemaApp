package com.example.MyCinemaApp.controllers;

import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.dto.GenresDto;
import com.example.MyCinemaApp.services.PopularService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class PopularPageController {

    final private PopularService popularService;

    @ModelAttribute("popularCinema")
    public List<CinemaNameDto> popularCinema(){
        return popularService.getCinemaByRating(9);
    }

    @GetMapping("/popular")
    public String genresPage(Model model){
        model.addAttribute("popularCinema", popularCinema());
        return "popular";
    }

}
