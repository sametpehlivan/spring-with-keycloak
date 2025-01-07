package com.sametp.example.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
//@Component
public class CustomizedJwtConverter implements Converter<Jwt,AbstractAuthenticationToken> {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public AbstractAuthenticationToken convert(Jwt source) {

        Collection<GrantedAuthority> roles = extractSourceRoles(source);
        return new JwtAuthenticationToken(source,roles,source.getSubject());
    }
    private Collection<GrantedAuthority> extractSourceRoles(Jwt source) {
        var resourceAccess = new HashMap<>(source.getClaim("realm_access"));
        List<String> eternal = objectMapper.convertValue(resourceAccess.get("roles"), new TypeReference<>() {});
        return eternal.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-","_")))
                .collect(Collectors.toSet());
    }
}
