package com.spacex.spacex.repository;

import com.spacex.spacex.entity.Launch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaunchRepository extends JpaRepository<Launch, Long> {
}
