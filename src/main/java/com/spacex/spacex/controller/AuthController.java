package com.spacex.spacex.controller;

import com.spacex.spacex.constants.ErrorConstants;
import com.spacex.spacex.dto.LoginDTO;
import com.spacex.spacex.dto.RegisterDTO;
import com.spacex.spacex.dto.RoleDTO;
import com.spacex.spacex.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "${spring.cors.host}", allowCredentials = "true")
public class AuthController {
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        String res = authService.register(registerDTO);
        if(res.equals(ErrorConstants.USERNAME_EXISTS)){
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try{
        String res = authService.login(loginDTO);
            if(res.equals(ErrorConstants.USERNAME_NOT_FOUND)){
                return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        catch(Exception e){
            if(e.getMessage().equals(ErrorConstants.BAD_CREDENTIALS)){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
            }
            else {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/logged")
    public ResponseEntity<Boolean> isLoggedIn(){
        return ResponseEntity.ok(authService.isLoggedIn());
    }
}