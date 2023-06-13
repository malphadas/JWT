package com.malpha.jwtauth.Config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

import static org.springframework.security.config.Customizer.*;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.key}")
    private String key;
    
    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
            User.withUsername("malpha")
                .password("{noop}password")
                .authorities("READ", "ROLE_USER")
                .build());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http = 
        http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth->auth
                .requestMatchers("/api/auth/token").hasRole("USER")
                .anyRequest().hasAnyAuthority("SCOPE_READ")
        )
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer((oauth2ResourceServer) ->
                 oauth2ResourceServer.jwt((jwt) -> jwt.decoder(jwtDecoder())))
        .httpBasic(withDefaults());
         return http.build();
     }

     @Bean
     JwtEncoder jwtEncoder(){
        return new NimbusJwtEncoder(new ImmutableSecret<>(key.getBytes()));
     }

     @Bean
     JwtDecoder jwtDecoder(){
        byte[] bytes = key.getBytes();
        SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length, "RSA");
        return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm(MacAlgorithm.HS512).build();

     }
        
}
 



