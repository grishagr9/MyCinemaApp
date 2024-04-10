package com.example.MyCinemaApp.services;

import com.example.MyCinemaApp.API.CinemaAPI;
import com.example.MyCinemaApp.API.ParserJSON;
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
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        log.info("User id = " + user.getId());
        Long userId = Long.valueOf(user.getId());
        var favorites = favoriteRepository.findAllByUserid(userId);

        List<CinemaNameDto> favoriteCinemas = new ArrayList<>();
        for(Long it: favorites){
            Long cinema_id = favoriteRepository.findById(it).get().getCinema_id();
            Cinema cinema = cinemaRepository.findCinemaById(cinema_id);
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

    public void addFavorite(Long cinemaId) {
        Cinema cinema = cinemaRepository.findByKp_id(cinemaId);
        if(cinema == null) {
            CinemaNameDto nameCinema = null;
            try {
                nameCinema = ParserJSON.parseOneFilm(CinemaAPI.getFilmByKPID(cinemaId)).get(0);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(nameCinema != null) {
                Cinema newCinema = new Cinema();
                newCinema.setName(nameCinema.getName());
                newCinema.setKp_id(nameCinema.getId());
                newCinema.setDescription(nameCinema.getDescription());
                newCinema.setPosterPhoto(nameCinema.getPosterPhoto());
                newCinema.setInternalRating(nameCinema.getInternalRating());
                newCinema.setDescription(nameCinema.getDescription());
                cinemaRepository.save(newCinema);
            }
        }
        Consumer user = userRepository.findByHash(Data.JWTtoken);
        if(user == null){
            Consumer newUser = new Consumer();
            newUser.setHash(Data.JWTtoken);
            newUser.setName("Bob");
            userRepository.save(newUser);

            user = newUser;
        }

        Favorite favorite = new Favorite();
        favorite.setCinema_id(Long.valueOf(cinema.getId()));
        favorite.setUser_id(Long.valueOf(user.getId()));
        favoriteRepository.save(favorite);

        log.info("Добавлен любимый фильм " + cinemaId + " " + cinema.getName());
    }

    public boolean containsCinema(Long cinemaId) {
        Consumer user = userRepository.findByHash(Data.JWTtoken);
        if(user == null){
            Consumer newUser = new Consumer();
            newUser.setHash(Data.JWTtoken);
            newUser.setName("Alice");
            userRepository.save(newUser);

            user = newUser;
            return false;
        }


        return !favoriteRepository.findByUserIdAndCinemaId(Long.valueOf(user.getId()), cinemaId).isEmpty();
    }
}
