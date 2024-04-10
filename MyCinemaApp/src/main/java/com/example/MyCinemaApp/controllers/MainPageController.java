package com.example.MyCinemaApp.controllers;

import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.dto.GenresDto;
import com.example.MyCinemaApp.dto.SearchWordDto;
import com.example.MyCinemaApp.errs.EmptySearchException;
import com.example.MyCinemaApp.services.CinemaService;
import com.example.MyCinemaApp.services.MainPageService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;
    private final CinemaService cinemaService;

    @ModelAttribute("filmRecent")
    public List<CinemaNameDto> filmRecent() throws IOException, ParseException, ExecutionException, InterruptedException {
        return mainPageService.getAllFilm();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<CinemaNameDto> searchResults(){
        return new ArrayList<>();
    }

    @GetMapping("/")
    public String mainPage(Model model) throws IOException, ParseException, ExecutionException, InterruptedException {
        Logger.getLogger(MainPageController.class.getName()).info("main Page class start work");
        model.addAttribute("filmRecent", filmRecent());
        return "index";
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResult(@PathVariable(value = "searchWord",required = false) SearchWordDto searchWordDto,
                                  Model model ) throws EmptySearchException {
        if(searchWordDto != null) {
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults", cinemaService
                    .getFilmsByName(searchWordDto.getExample()).stream().filter(x -> x.getInternalRating()>1).toList());
            return "search_index";
        }else{
            throw new EmptySearchException("Поиск невозможен по null");
        }
    }
}
