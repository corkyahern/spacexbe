package com.spacex.spacex.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacex.spacex.dto.RocketDTO;
import com.spacex.spacex.entity.Launch;
import com.spacex.spacex.entity.Rocket;
import com.spacex.spacex.exception.ResourceNotFoundException;
import com.spacex.spacex.repository.LaunchRepository;
import com.spacex.spacex.repository.RocketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@AutoConfigureMockMvc
@WithMockUser(username = "spring")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LaunchControllerITests {
    @Autowired
    private LaunchRepository launchRepository;
    @Autowired
    private RocketRepository rocketRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String TEST_ROCKET = "Old Faithful";
    private Launch buildLaunch(){
        return Launch
                .builder()
                .date(LocalDate.parse("2024-07-25"))
                .success(true)
                .build();
    }

    private void makeAssertions(ResultActions response, Launch launch, boolean created) throws Exception {
        response.andDo(print())
                .andExpect(created ? status().isCreated() : status().isOk())
                .andExpect(jsonPath("$.date", is(launch.getDate().toString()    )))
                .andExpect(jsonPath("$.success", is(launch.isSuccess())));
    }

    @BeforeEach
    public void setup(){
        launchRepository.deleteAll();
        rocketRepository.deleteAll();
        if(!rocketRepository.existsByName(TEST_ROCKET)){
            Rocket rocket = Rocket
                    .builder()
                    .name(TEST_ROCKET)
                    .weightInPounds(250000)
                    .build();
            rocketRepository.save(rocket);
        }
    }

    @Test
    public void givenLaunch_whenAddLaunch_thenReturnLaunch() throws Exception {
        Launch launch = buildLaunch();
        ResultActions response = mockMvc.perform(post("/api/launches/rocketname/{rocketname}", TEST_ROCKET)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(launch)));
        makeAssertions(response, launch, true);
    }

    @Test
    public void givenLaunch_whenGetById_thenReturnLaunch() throws Exception {
        Launch launch = launchRepository.save(buildLaunch());
        ResultActions response = mockMvc.perform(get("/api/launches/{id}", launch.getId()));
        makeAssertions(response, launch, false);
    }

    @Test
    public void givenLaunches_whenGetAllLaunches_thenReturnLaunches() throws Exception {
        int rocket_size = 1;
        Set<Launch> launchList = new HashSet<>();
        launchList.add(buildLaunch());
        launchList.add(Launch
                .builder()
                .date(LocalDate.parse("2024-07-20"))
                .success(true)
                .build());
        Rocket rocket = rocketRepository.findByName(TEST_ROCKET)
                .orElseThrow(() -> new ResourceNotFoundException("Rocket", "name", TEST_ROCKET));
        launchList.forEach((launch -> {
            launch.setRocket(rocket);
        }));
        launchRepository.saveAll(launchList);
        ResultActions response = mockMvc.perform(get("/api/launches"));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(rocket_size)));
    }

    @Test
    public void givenLaunch_whenUpdateLaunch_thenReturnLaunch() throws Exception {
        Launch launch = launchRepository.save(buildLaunch());
        launch.setSuccess(false);
        ResultActions response = mockMvc.perform(put("/api/launches/{id}", launch.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(launch)));
        makeAssertions(response, launch, false);
    }

    @Test
    public void givenLaunch_whenDeleteLaunch_thenReturnString() throws Exception {
        Launch launch = launchRepository.save(buildLaunch());
        ResultActions response = mockMvc.perform(delete("/api/launches/{id}", launch.getId()));
        response.andDo(print())
                .andExpect(status().isOk());
    }
}
