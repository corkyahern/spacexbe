package com.spacex.spacex.service;

import com.spacex.spacex.dto.RocketDTO;

import java.util.List;

public interface RocketService {
    RocketDTO addRocket(RocketDTO rocketDTO);
    RocketDTO getById(long id);
    RocketDTO getByName(String name);
    List<RocketDTO> getAllRockets();
    RocketDTO updateRocket(RocketDTO rocketDTO, long id);
    void deleteRocket(long id);
}
