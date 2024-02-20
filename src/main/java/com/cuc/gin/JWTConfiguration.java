package com.cuc.gin;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

/**
 * @author : Wang SM.
 * @since : 2024/2/25, 周二
 **/
@Configuration
public class JWTConfiguration {
    @Bean(name = "jwtKey")
    public Key jwtKey(){
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
