package com.example.hotels.generator;

import com.example.hotels.data.Role;
import com.example.hotels.data.model.Hotel;
import com.example.hotels.data.model.Room;
import com.example.hotels.data.model.User;
import com.example.hotels.data.repository.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
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
            Hotel hotel1 = new Hotel();
            hotel1.setCity("New York");
            hotel1.setName("Sofitel");
            hotel1.setStars("****");

            Room room1 = new Room();
            room1.setHotel(hotel1);
            room1.setRoom_type("King");
            room1.setRoom_price(new BigDecimal(220.0));

            Room room2 = new Room();
            room2.setHotel(hotel1);
            room2.setRoom_type("Queen");
            room2.setRoom_price(new BigDecimal(200.0));

            Hotel hotel2 = new Hotel();
            hotel2.setCity("San Francisco");
            hotel2.setName("W");
            hotel2.setStars("****");

            Room room3 = new Room();
            room3.setHotel(hotel1);
            room3.setRoom_type("King");
            room3.setRoom_price(new BigDecimal(260.0));

            Room room4 = new Room();
            room4.setHotel(hotel1);
            room4.setRoom_type("Queen");
            room4.setRoom_price(new BigDecimal(220.0));

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
