package com.example.MyCinemaApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActorPageController {

    @GetMapping("/actors")
    public String getPage(){
        return "actors";
    }
}
