package com.spacex.spacex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaunchDTO {
    private long id;
    private LocalDate date;
    private boolean success;
    private RocketDTO rocket;
}
