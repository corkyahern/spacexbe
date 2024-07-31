package com.spacex.spacex;

import com.spacex.spacex.entity.Role;
import com.spacex.spacex.repository.RoleRepository;
import com.spacex.spacex.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpacexApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpacexApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(RoleRepository roleRepository, UserRepository userRepository) {
		return args -> {
			userRepository.deleteAll();
			roleRepository.deleteAll();
			Role role = new Role();
			role.setName("ROLE_USER");
			roleRepository.save(role);
		};
	}
}
