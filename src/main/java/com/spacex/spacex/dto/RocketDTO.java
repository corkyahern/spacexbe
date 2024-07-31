package com.spacex.spacex.dto;

import com.spacex.spacex.entity.Launch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RocketDTO {
    private long id;
    private String name;
    private int weightInPounds;
    private Set<Launch> launches;
}
