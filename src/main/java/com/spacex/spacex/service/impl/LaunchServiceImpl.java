package com.spacex.spacex.service.impl;

import com.spacex.spacex.dto.LaunchDTO;
import com.spacex.spacex.entity.Launch;
import com.spacex.spacex.entity.Rocket;
import com.spacex.spacex.exception.ResourceNotFoundException;
import com.spacex.spacex.repository.LaunchRepository;
import com.spacex.spacex.repository.RocketRepository;
import com.spacex.spacex.service.LaunchService;
import com.spacex.spacex.service.RocketService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LaunchServiceImpl implements LaunchService {
    private LaunchRepository launchRepository;
    private RocketRepository rocketRepository;
    private ModelMapper mapper;

    @Override
    public LaunchDTO addLaunch(LaunchDTO launchDTO, String rocketName) {
        Launch launch = mapper.map(launchDTO, Launch.class);
        Rocket rocket = rocketRepository.findByName(rocketName)
                .orElseThrow(() -> new ResourceNotFoundException("Rocket", "name", rocketName));
        launch.setRocket(rocket);
        Launch rv = launchRepository.save(launch);
        return mapper.map(rv, LaunchDTO.class);
    }

    @Override
    public LaunchDTO getById(long id) {
        Launch launch = launchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rocket", "id", Long.toString(id)));
        return mapper.map(launch, LaunchDTO.class);
    }

    @Override
    public List<LaunchDTO> getAllLaunches() {
        List<Launch> launches = launchRepository.findAll();
        return launches
                .stream()
                .map((launch) -> mapper.map(launch, LaunchDTO.class))
                .toList();
    }

    @Override
    public LaunchDTO updateLaunch(LaunchDTO launchDTO, long id) {
        LaunchDTO launchDTO1 = getById(id);
        launchDTO1.setDate(launchDTO.getDate());
        launchDTO1.setSuccess(launchDTO.isSuccess());
        Launch launch = launchRepository.save(mapper.map(launchDTO1, Launch.class));
        return mapper.map(launch, LaunchDTO.class);
    }

    @Override
    public void deleteLaunch(long id) {
        launchRepository.deleteById(id);
    }
}
