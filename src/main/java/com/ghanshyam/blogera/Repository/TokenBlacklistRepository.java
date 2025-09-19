package com.ghanshyam.blogera.Repository;

import com.ghanshyam.blogera.auth.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<BlacklistedToken, String> {

    boolean existsByToken(String token);

}
