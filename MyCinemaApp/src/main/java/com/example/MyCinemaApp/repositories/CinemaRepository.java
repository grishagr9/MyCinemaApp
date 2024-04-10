package com.example.MyCinemaApp.repositories;

import com.example.MyCinemaApp.entity.Cinema;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Cinema findCinemaById(Long id);

    Cinema findCinemaByName(String name);

    @Query(value = "SELECT * FROM cinema WHERE kp_id = :id", nativeQuery = true)
    Cinema findByKp_id(Long id);
}
