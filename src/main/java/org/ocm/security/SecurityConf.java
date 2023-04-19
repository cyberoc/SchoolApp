package org.ocm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConf {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors().disable().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/status").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().denyAll();
        http.oauth2ResourceServer().jwt();

        return http.build();
    }
}
