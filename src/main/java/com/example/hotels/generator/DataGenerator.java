package com.example.hotels.generator;

import com.example.hotels.data.Role;
import com.example.hotels.data.model.User;
import com.example.hotels.data.repository.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder,
                                      UserRepository userRepository) {
        return args -> {
            if (userRepository.count() != 0L) {
                return;
            }

            User user = new User();
            user.setUsername("user");
            user.setHashedPassword(passwordEncoder.encode("user"));
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            User admin = new User();
            admin.setUsername("admin");
            admin.setHashedPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(Role.USER, Role.ADMIN));
            userRepository.save(admin);


        };
    }

}
