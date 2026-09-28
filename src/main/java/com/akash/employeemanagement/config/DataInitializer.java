package com.akash.employeemanagement.config;

import com.akash.employeemanagement.entity.User;
import com.akash.employeemanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createDefaultUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Create USER
            if (userRepository.findByUsername("akash").isEmpty()) {

                User user = new User();

                user.setUsername("akash");
                user.setPassword(passwordEncoder.encode("akash123"));
                user.setRole("USER");

                userRepository.save(user);
            }

            // Create ADMIN
            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User();

                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");

                userRepository.save(admin);
            }
        };
    }
}