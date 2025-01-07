package com.sametp.example.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    //@Autowired
    //CustomizedJwtConverter jwtConverter;
    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs*/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests( auth -> {
            auth.requestMatchers(HttpMethod.GET,"/tutorials").permitAll()
                    .requestMatchers(HttpMethod.GET,"/tutorials/*").permitAll()
                    .requestMatchers(AUTH_WHITELIST).permitAll()
                    .anyRequest().authenticated();
        });
        http.oauth2ResourceServer( server -> {
                server.jwt(jwtConfigurer ->{

                    jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter());
                });
        });
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        return http.build();
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        // Property (scope) named roles was added to the keycloak token, therefore,
        //configuration was made by giving only the property name in the default converter.
        JwtGrantedAuthoritiesConverter rolesConverter = new JwtGrantedAuthoritiesConverter();
        rolesConverter.setAuthorityPrefix(""); // SCOPE_ by default
        rolesConverter.setAuthoritiesClaimName("roles"); // scope or scp by default
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(rolesConverter);
        return converter;
    }
    //if you want to use it without ROLE_ prefix ,I didn't use this because I added ROLE_ prefix in keycloak
    /*
        public DefaultMethodSecurityExpressionHandler methodSecurityHandler(){
            DefaultMethodSecurityExpressionHandler methodSecurityHandler =
                    new DefaultMethodSecurityExpressionHandler();
            methodSecurityHandler.setDefaultRolePrefix(""); //ROLE_ by default
            return methodSecurityHandler;
        }
   */
}
