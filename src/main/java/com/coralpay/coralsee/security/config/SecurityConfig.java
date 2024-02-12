package com.coralpay.coralsee.security.config;

import com.coralpay.coralsee.security.filters.CoralseeUsernamePasswordAuthenticationFilter;
import com.coralpay.coralsee.security.filters.JwtAuthorizationFilter;
import com.coralpay.coralsee.security.manager.CoralseeAuthenticationManager;
import com.coralpay.coralsee.security.providers.CoralseeUsernamePasswordAuthenticationProvider;
import com.coralpay.coralsee.security.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(){
        Set<AuthenticationProvider> providers = Set.of(new CoralseeUsernamePasswordAuthenticationProvider(userDetailsService, passwordEncoder));
        return new CoralseeAuthenticationManager(providers);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(c->c.sessionCreationPolicy(STATELESS))
                .addFilterAt(new CoralseeUsernamePasswordAuthenticationFilter(authenticationManager(), jwtService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtService), CoralseeUsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c -> c.requestMatchers("/login", "/api/v1/signup").permitAll())
//                .authorizeHttpRequests(c->c.requestMatchers("/api/v1/pay").hasAnyAuthority(USER.name()))
                .authorizeHttpRequests(c -> c.anyRequest().authenticated())
                .build();
    }
}
