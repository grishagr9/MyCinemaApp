package com.example.MyCinemaApp.repositories;

import com.example.MyCinemaApp.entity.Consumer;
import com.example.MyCinemaApp.entity.links.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query(value = "SELECT * FROM favorite WHERE user_id = :id", nativeQuery = true)
    List<Long> findAllByUserid(Long id);
}