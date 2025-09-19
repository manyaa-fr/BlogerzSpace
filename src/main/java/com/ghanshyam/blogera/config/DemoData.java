package com.ghanshyam.blogera.config;

import com.ghanshyam.blogera.user.AppUser;
import com.ghanshyam.blogera.user.AppUserRepository;
import com.ghanshyam.blogera.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class DemoData {

    @Bean
    CommandLineRunner initUsers(AppUserRepository repository) {
        return args -> {
            if (repository.findByUsername("ghanshyam").isEmpty()) {
                AppUser user = new AppUser();
                user.setUsername("ghanshyam");
                user.setPassword(new BCryptPasswordEncoder().encode("1234"));
                user.setRoles(Set.of(Role.ROLE_USER));
                repository.save(user);
            }
            if (repository.findByUsername("arnav").isEmpty()) {
                AppUser user2 = new AppUser();
                user2.setUsername("arnav");
                user2.setPassword(new BCryptPasswordEncoder().encode("1234"));
                user2.setRoles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER));
                repository.save(user2);
            }
        };
    }
}
