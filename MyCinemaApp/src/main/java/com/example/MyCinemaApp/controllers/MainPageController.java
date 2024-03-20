package com.example.MyCinemaApp.controllers;

import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.dto.GenresDto;
import com.example.MyCinemaApp.services.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @ModelAttribute("filmRecent")
    public List<CinemaNameDto> filmRecent(){
        return mainPageService.getAllFilm();
    }

    @GetMapping("/")
    public String mainPage(Model model){
        Logger.getLogger(MainPageController.class.getName()).info("main Page class start work");
        model.addAttribute("filmRecent", filmRecent());
        return "index";
    }


}
