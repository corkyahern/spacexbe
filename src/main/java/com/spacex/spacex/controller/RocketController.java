package com.spacex.spacex.controller;

import com.spacex.spacex.dto.RocketDTO;
import com.spacex.spacex.service.RocketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rockets")
@CrossOrigin(origins = "${spring.cors.host}", allowCredentials = "true")
@AllArgsConstructor
public class RocketController {
    private RocketService rocketService;

    @PostMapping
    public ResponseEntity<RocketDTO> addRocket(@RequestBody RocketDTO rocketDTO){
        return new ResponseEntity<>(rocketService.addRocket(rocketDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RocketDTO> getById(@PathVariable long id){
        return ResponseEntity.ok(rocketService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RocketDTO> getByName(@PathVariable String name){
        return ResponseEntity.ok(rocketService.getByName(name));
    }

    @GetMapping
    public ResponseEntity<List<RocketDTO>> getAllRockets(){
        return ResponseEntity.ok(rocketService.getAllRockets());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RocketDTO> updateRocket(@RequestBody RocketDTO rocketDTO,
                                                  @PathVariable long id){
        return ResponseEntity.ok(rocketService.updateRocket(rocketDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRocket(@PathVariable long id){
        rocketService.deleteRocket(id);
        return ResponseEntity.ok("Rocket deleted successfully.");
    }
}
