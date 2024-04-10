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

    /**
     * нахождение всех избранных фильмов у пользователя
     * @return
     */
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

    /**
     * entity -> data transfer object
     * @param cinema entity
     * @return dto
     */
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

    /**
     * добавление фильма в избраное у пользователя
     * @param cinemaId id фильма на кинопоиске
     */
    public void addFavorite(Long cinemaId) {
        Cinema cinema = cinemaRepository.findByKp_id(cinemaId);
        if(cinema == null) {
            CinemaNameDto nameCinema = null;
            try {
                var list = ParserJSON.parse(CinemaAPI.getFilmByKPID(cinemaId));
                if(!list.isEmpty()){
                    nameCinema = list.get(0);
                }
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
                cinema = new Cinema();
                cinema.setName(nameCinema.getName());
                cinema.setKp_id(nameCinema.getId());
                cinema.setDescription(nameCinema.getDescription());
                cinema.setPosterPhoto(nameCinema.getPosterPhoto());
                cinema.setInternalRating(nameCinema.getInternalRating());
                cinema.setDescription(nameCinema.getDescription().substring(0,255));
                cinemaRepository.save(cinema);
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

    /**
     * проверка есть ли данный фильм у данного пользователя
     * @param kp_id id фильма на кинопоиске
     * @return имеется фильм?
     */
    public boolean containsCinema(Long kp_id) {
        Consumer user = userRepository.findByHash(Data.JWTtoken);
        if(user == null){
            Consumer newUser = new Consumer();
            newUser.setHash(Data.JWTtoken);
            newUser.setName("Alice");
            userRepository.save(newUser);

            return false;
        }

        Cinema cinema = cinemaRepository.findByKp_id(kp_id);
        long cinema_id = 0;
        if(cinema != null){
            cinema_id = cinema.getId();
        }

        return !favoriteRepository.findByUserIdAndCinemaId(Long.valueOf(user.getId()), cinema_id).isEmpty();
    }
}
