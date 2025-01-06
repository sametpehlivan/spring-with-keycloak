package com.sametp.example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests( auth -> {
           auth.anyRequest().authenticated();
        });
        http.oauth2ResourceServer( server -> {
            //-> for jwt token
                // server.jwt(Customizer.withDefaults());
            //-> for opaqueToken
                /*
                    jwt token resource server itself extracts and checks the token, but since the opaque token is a random string,
                    we have to have it checked by the authorization server. Check the application.yml file.
                 */
            server.opaqueToken(Customizer.withDefaults());
        });
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        return http.build();
    }
}
