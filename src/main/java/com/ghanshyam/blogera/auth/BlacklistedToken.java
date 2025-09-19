package com.ghanshyam.blogera.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BlacklistedToken {

    @Id
    private String token;

}
