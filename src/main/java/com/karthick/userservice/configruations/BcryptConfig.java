package com.karthick.userservice.configruations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BcryptConfig {
    @Bean
    public BCryptPasswordEncoder provideBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
