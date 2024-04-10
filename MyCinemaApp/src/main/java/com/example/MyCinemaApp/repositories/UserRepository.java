package com.example.MyCinemaApp.repositories;

import com.example.MyCinemaApp.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Consumer, Long> {

    Consumer findByHash(String hash);
}
