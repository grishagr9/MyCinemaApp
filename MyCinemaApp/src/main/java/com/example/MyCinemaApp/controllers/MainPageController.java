package com.example.MyCinemaApp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    @GetMapping("/")
    public String mainPage(Model model){
        Logger.getLogger(MainPageController.class.getName()).info("main Page class start work");
        return "index";
    }

    @GetMapping("/genres")
    public String genresPage(Model model){
        Logger.getLogger(MainPageController.class.getName()).info("main Page class start work");
        return "index";
    }
}
