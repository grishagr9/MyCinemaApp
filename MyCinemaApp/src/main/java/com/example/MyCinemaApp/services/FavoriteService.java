package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.data.Data;
import com.example.MyCinemaApp.dto.CinemaNameDto;
import com.example.MyCinemaApp.entity.Cinema;
import com.example.MyCinemaApp.entity.Consumer;
import com.example.MyCinemaApp.entity.Genre;
import com.example.MyCinemaApp.entity.links.Favorite;
import com.example.MyCinemaApp.repositories.CinemaRepository;
import com.example.MyCinemaApp.repositories.FavoriteRepository;
import com.example.MyCinemaApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {

    private final UserRepository userRepository;
    private final CinemaRepository cinemaRepository;
    private final FavoriteRepository favoriteRepository;

    public List<CinemaNameDto> getAllCinemas() {
        String userHash = Data.JWTtoken;
        Consumer user = userRepository.findByHash(userHash);
        if(user == null){
            log.info("No user found by hash " + userHash);
            return null;
        }
        Long userId = Long.valueOf(user.getId());
        var favorites = favoriteRepository.findAllByUserid(userId);

        List<CinemaNameDto> favoriteCinemas = new ArrayList<>();
        for(Long it: favorites){
            Cinema cinema = cinemaRepository.findCinemaById(it);
            if(cinema != null){
                favoriteCinemas.add(toDTO(cinema));
            }
        }


        return favoriteCinemas;
    }


    private CinemaNameDto toDTO(Cinema cinema) {
        CinemaNameDto result = new CinemaNameDto();

        result.setInternalRating(cinema.getInternalRating());
        result.setPosterPhoto(cinema.getPosterPhoto());
        var genreSet = cinema.getGenreSet();
        if(genreSet!=null) {
            result.setGenres(genreSet.stream().map(Genre::getName).toList());
        }
        result.setDescription(cinema.getDescription());
        result.setName(cinema.getName());
        result.setId(cinema.getKp_id());

        return result;
    }

    public void addFavorite(CinemaNameDto nameCinema) {
        Consumer user = userRepository.findByHash(Data.JWTtoken);
        if(user == null){
            Consumer newUser = new Consumer();
            newUser.setHash(Data.JWTtoken);
            newUser.setName("Bob");
            userRepository.save(newUser);

            user = newUser;
        }

        Cinema cinema = new Cinema();
        cinema.setName(nameCinema.getName());
        cinema.setKp_id(nameCinema.getId());
        cinema.setDescription(nameCinema.getDescription());
        cinema.setPosterPhoto(nameCinema.getPosterPhoto());
        cinema.setInternalRating(nameCinema.getInternalRating());
        cinema.setDescription(nameCinema.getDescription());
        cinemaRepository.save(cinema);

        Favorite favorite = new Favorite();
        favorite.setCinema_id(nameCinema.getId());
        favorite.setUser_id(Long.valueOf(user.getId()));
        favoriteRepository.save(favorite);
    }
}
