package com.spacex.spacex.controller;

import com.spacex.spacex.dto.LaunchDTO;
import com.spacex.spacex.dto.RocketDTO;
import com.spacex.spacex.service.LaunchService;
import com.spacex.spacex.service.RocketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/launches")
@AllArgsConstructor
@CrossOrigin(origins = "${spring.cors.host}", allowCredentials = "true")
public class LaunchController {
    private LaunchService launchService;
    private RocketService rocketService;

    @PostMapping("/rocketname/{rocketname}")
    public ResponseEntity<LaunchDTO> addLaunch(@RequestBody LaunchDTO launchDTO,
                                               @PathVariable("rocketname") String rocketName){
            return new ResponseEntity<>(launchService.addLaunch(launchDTO, rocketName), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaunchDTO> getById(@PathVariable long id){
        return ResponseEntity.ok(launchService.getById(id));
    }

    /* Get Launches grouped by Rocket */
    @GetMapping
    public ResponseEntity<List<RocketDTO>> getAllLaunches(){
        return ResponseEntity.ok(rocketService.getAllRockets());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaunchDTO> updateLaunch(@RequestBody LaunchDTO launchDTO,
                                                  @PathVariable long id){
        return ResponseEntity.ok(launchService.updateLaunch(launchDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLaunch(@PathVariable long id){
        launchService.deleteLaunch(id);
        return ResponseEntity.ok("Launch deleted successfully.");
    }

}
