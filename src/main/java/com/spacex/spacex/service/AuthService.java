package com.spacex.spacex.service;

import com.spacex.spacex.dto.LoginDTO;
import com.spacex.spacex.dto.RegisterDTO;

public interface AuthService {
    String register(RegisterDTO registerDTO);
    String login(LoginDTO loginDTO);
    boolean isLoggedIn();
}
