package com.ghanshyam.blogera.auth;

import com.ghanshyam.blogera.Repository.TokenBlacklistRepository;
import com.ghanshyam.blogera.user.AppUser;
import com.ghanshyam.blogera.user.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private TokenBlacklistRepository blacklistRepo;
    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AppUserRepository appUserRepository) {
        this.jwtUtil = jwtUtil;
        this.appUserRepository = appUserRepository;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt = extractToken(request);
        if (jwt != null && blacklistRepo.existsByToken(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token is blacklisted (logged out)");
            return;
        }
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            String token = authHeader.substring(7); // remove "Bearer "
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AppUser appUser = appUserRepository.findByUsername(username).orElse(null);

                if (appUser != null && jwtUtil.validateToken(token, appUser)) {
                    var auth = new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
