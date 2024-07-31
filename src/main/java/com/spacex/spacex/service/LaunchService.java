package com.spacex.spacex.service;

import com.spacex.spacex.dto.LaunchDTO;

import java.util.List;

public interface LaunchService {
    LaunchDTO addLaunch(LaunchDTO launchDTO, String rocketName);
    LaunchDTO getById(long id);
    List<LaunchDTO> getAllLaunches();
    LaunchDTO updateLaunch(LaunchDTO launchDTO, long id);
    void deleteLaunch(long id);
}
