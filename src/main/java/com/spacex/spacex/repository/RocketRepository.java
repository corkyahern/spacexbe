package com.spacex.spacex.repository;

import com.spacex.spacex.entity.Rocket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RocketRepository extends JpaRepository<Rocket, Long> {
    Optional<Rocket> findByName(String name);
    boolean existsByName(String name);
}
