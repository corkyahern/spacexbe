package com.spacex.spacex.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacex.spacex.entity.Rocket;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@AutoConfigureMockMvc
@WithMockUser(username = "spring")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RocketControllerITests {
    @Autowired
    private RocketRepository rocketRepository;
    @Autowired
    private LaunchRepository launchRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Rocket buildRocket(){
        return Rocket
                .builder()
                .name("Old Faithful")
                .weightInPounds(250000)
                .build();
    }
    private void makeAssertions(ResultActions response, Rocket rocket, boolean created) throws Exception {
        response.andDo(print())
                .andExpect(created ? status().isCreated(): status().isOk())
                .andExpect(jsonPath("$.name", is(rocket.getName())))
                .andExpect(jsonPath("$.weightInPounds", is(rocket.getWeightInPounds())));
    }

    @BeforeEach
    public void setup(){
        launchRepository.deleteAll();
        rocketRepository.deleteAll();
    }

    @Test
    public void givenRocket_whenAddRocket_thenReturnRocket() throws Exception {
        Rocket rocket = buildRocket();
        ResultActions response = mockMvc.perform(post("/api/rockets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rocket)));
        makeAssertions(response, rocket, true);
    }

    @Test
    public void givenRocket_whenGetById_thenReturnRocket() throws Exception {
        Rocket rocket = rocketRepository.save(buildRocket());
        ResultActions response = mockMvc.perform(get("/api/rockets/{id}", rocket.getId()));
        makeAssertions(response, rocket, false);
    }

    @Test
    public void givenRocket_whenGetByName_thenReturnRocket() throws Exception {
        Rocket rocket = rocketRepository.save(buildRocket());
        ResultActions response = mockMvc.perform(get("/api/rockets/name/{name}", rocket.getName()));
        makeAssertions(response, rocket, false);
    }

    @Test
    public void givenRockets_whenGetAllRockets_thenReturnListOfRockets() throws Exception {
        List<Rocket> rocketList = new ArrayList<>();
        rocketList.add(buildRocket());
        rocketList.add(Rocket
                .builder()
                .name("Great Journey")
                .weightInPounds(290000)
                .build());
        rocketRepository.saveAll(rocketList);
        ResultActions response = mockMvc.perform(get("/api/rockets"));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(rocketList.size())));
    }

    @Test
    public void givenRocket_whenUpdateRocket_thenReturnRocket() throws Exception {
        Rocket rocket = rocketRepository.save(buildRocket());
        rocket.setWeightInPounds(240000);
        ResultActions response = mockMvc.perform(put("/api/rockets/{id}", rocket.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rocket)));
        makeAssertions(response, rocket, false);
    }

    @Test
    public void givenRocket_whenDeleteRocket_thenReturnString() throws Exception {
        Rocket rocket = rocketRepository.save(buildRocket());
        ResultActions response = mockMvc.perform(delete("/api/rockets/{id}", rocket.getId()));
        response.andDo(print())
                .andExpect(status().isOk());
    }
}
