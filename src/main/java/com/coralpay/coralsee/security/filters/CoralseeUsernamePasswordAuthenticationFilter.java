package com.coralpay.coralsee.security.filters;

import com.coralpay.coralsee.dtos.requests.LoginRequest;
import com.coralpay.coralsee.security.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

import static com.coralpay.coralsee.utils.Constants.ACCESS_TOKEN_FIELD_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class CoralseeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ObjectMapper mapper = new ObjectMapper();

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword());
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;
        } catch (IOException exception) {
            throw new AuthenticationServiceException(exception.getMessage(), exception.getCause());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String token = jwtService.generateTokenFor(authResult.getPrincipal().toString());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(Map.of(ACCESS_TOKEN_FIELD_VALUE, token)));
        response.flushBuffer();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getOutputStream().write(mapper.writeValueAsBytes(Map.of("message", exception.getMessage())));
        response.flushBuffer();
    }

}
