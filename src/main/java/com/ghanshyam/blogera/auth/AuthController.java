package com.ghanshyam.blogera.auth;

import com.ghanshyam.blogera.Repository.TokenBlacklistRepository;
import com.ghanshyam.blogera.dto.SignUpRequest;
import com.ghanshyam.blogera.user.AppUser;
import com.ghanshyam.blogera.user.AppUserRepository;
import com.ghanshyam.blogera.dto.LoginRequest;
import com.ghanshyam.blogera.user.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    public AuthController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        AppUser appUser = appUserRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String token = jwtUtil.generateToken(appUser);
        return new JwtResponse(jwtUtil.generateToken(appUser));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (appUserRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(signUpRequest.getUsername());
//        appUser.setPassword(signUpRequest.getPassword());
        appUser.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        appUser.setRoles(Set.of(Role.ROLE_USER));

        appUserRepository.save(appUser);
        return ResponseEntity.ok("User is registered successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            String token = authHeader.substring(7);

            if (!tokenBlacklistRepository.existsByToken(token)) {
                BlacklistedToken blacklistedToken = new BlacklistedToken();
                blacklistedToken.setToken(token);
                tokenBlacklistRepository.save(blacklistedToken);
            }
            return ResponseEntity.ok("Successfully logged out");
        }
        return ResponseEntity.badRequest().body("No token found in authorization header");
    }

}

