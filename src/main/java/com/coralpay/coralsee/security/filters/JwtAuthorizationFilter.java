package com.coralpay.coralsee.security.filters;

import com.coralpay.coralsee.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

import static com.coralpay.coralsee.utils.Constants.BEARER_TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isRequestToPublicEndpoint = getPublicEndpoints().contains(request.getServletPath());
        if (isRequestToPublicEndpoint) filterChain.doFilter(request, response);
        else authorizeRequest(request, response, filterChain);
    }

    private void authorizeRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authHeader = request.getHeader(AUTHORIZATION);
        boolean isRequestWithAuthorizationHeader = authHeader != null;
        if (isRequestWithAuthorizationHeader) authorize(authHeader);
        filterChain.doFilter(request, response);
    }

    private void authorize(String authHeader) {
        String token = extractTokenFrom(authHeader);
        boolean isValidToken = jwtService.validate(token);
        if (isValidToken) addUserCredentialsToContext(token);
    }

    private static String extractTokenFrom(String authHeader) {
        return authHeader.substring(BEARER_TOKEN_PREFIX.length());
    }

    private void addUserCredentialsToContext(String token) {
        UserDetails userDetails = jwtService.extractUserDetailsFrom(token);
        log.info("userDetails-->{}",  userDetails);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Set<String> getPublicEndpoints() {
        return Set.of("/login");
    }
}
