package com.example.MyCinemaApp.controllers;

import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.dto.SearchWordDto;
import com.example.MyCinemaApp.errs.EmptySearchException;
import com.example.MyCinemaApp.services.FindCinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CinemaSlugController {

    final private FindCinemaService findCinemaService;

    @ModelAttribute("slugFilm")
    public CinemaNameDto slugFilm(){
        return new CinemaNameDto();
    }
    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<CinemaNameDto> searchResults(){
        return new ArrayList<>();
    }

    @GetMapping( "/cinema_slug/{slugFilm}")
    public String getSearchResult(@PathVariable(value = "slugFilm",required = false) String slugFilm,
                                  Model model ) throws EmptySearchException {
        if(slugFilm != null){
            model.addAttribute("slugFilm", findCinemaService.findCinemaByName(slugFilm));
            return "cinema_slug";
        }else{
            throw new EmptySearchException("Не существует пустого фильма :(");
        }
    }
}
