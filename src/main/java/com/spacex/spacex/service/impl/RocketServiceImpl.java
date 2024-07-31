package com.spacex.spacex.service.impl;

import com.spacex.spacex.dto.RocketDTO;
import com.spacex.spacex.entity.Rocket;
import com.spacex.spacex.exception.ResourceNotFoundException;
import com.spacex.spacex.repository.RocketRepository;
import com.spacex.spacex.service.RocketService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RocketServiceImpl implements RocketService {
    private RocketRepository rocketRepository;
    private ModelMapper mapper;

    @Override
    public RocketDTO addRocket(RocketDTO rocketDTO) {
        Rocket rocket = rocketRepository.save(mapper.map(rocketDTO, Rocket.class));
        return mapper.map(rocket, RocketDTO.class);
    }

    @Override
    public RocketDTO getById(long id) {
        Rocket rocket = rocketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rocket", "id", Long.toString(id)));
        return mapper.map(rocket, RocketDTO.class);
    }

    @Override
    public RocketDTO getByName(String name) {
        Rocket rocket = rocketRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Rocket", "name", name));
        return mapper.map(rocket, RocketDTO.class);
    }

    @Override
    public List<RocketDTO> getAllRockets() {
        List<Rocket> rockets = rocketRepository.findAll();
        return rockets
                .stream()
                .map((rocket) -> mapper.map(rocket, RocketDTO.class))
                .toList();
    }

    @Override
    public RocketDTO updateRocket(RocketDTO rocketDTO, long id) {
        RocketDTO rocketDTO1 = getById(id);
        rocketDTO1.setName(rocketDTO.getName());
        rocketDTO1.setWeightInPounds(rocketDTO.getWeightInPounds());
        Rocket rv = rocketRepository.save(mapper.map(rocketDTO1, Rocket.class));
        return mapper.map(rv, RocketDTO.class);
    }

    @Override
    public void deleteRocket(long id) {
        rocketRepository.deleteById(id);
    }
}
