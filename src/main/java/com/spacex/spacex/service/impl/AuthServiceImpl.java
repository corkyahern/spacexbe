package com.spacex.spacex.service.impl;

import com.spacex.spacex.constants.ErrorConstants;
import com.spacex.spacex.dto.LoginDTO;
import com.spacex.spacex.dto.RegisterDTO;
import com.spacex.spacex.entity.Role;
import com.spacex.spacex.entity.User;
import com.spacex.spacex.exception.ResourceNotFoundException;
import com.spacex.spacex.repository.RoleRepository;
import com.spacex.spacex.repository.UserRepository;
import com.spacex.spacex.service.AuthService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private ModelMapper mapper;

    @Override
    public String register(RegisterDTO registerDTO) {
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            return ErrorConstants.USERNAME_EXISTS;
        }
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Name", "ROLE_USER"));
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        return "User created successfully.";
    }

    @Override
    public String login(LoginDTO loginDTO) {
        if(!userRepository.existsByUsername(loginDTO.getUsername())){
            return ErrorConstants.USERNAME_NOT_FOUND;
        }
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
            ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User logged in successfully.";
    }

    @Override
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !authentication.getName().equals("anonymousUser");
    }
}
