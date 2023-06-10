package com.jwt.jwtauth.Config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jose.JwaAlgorithm;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public InMemoryUserDetailsManager user() {
        return new InMemoryUserDetailsManager(
                User.withUsername("usuario")
                    .password("{noop}senha")
                    .authorities("READ","ROLE_USER")
                    .build()
        );
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests(auth->{
                auth.requestMatchers("/").permitAll();
                auth.anyRequest().authenticated();
            })
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //.oauth2ResourceServer(oauth2 -> oauth2.jwt())
            .formLogin(Customizer.withDefaults());
        return http.build();
    }
}