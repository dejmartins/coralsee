package com.coralpay.coralsee.services.user_management_service;

import com.coralpay.coralsee.dtos.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    @Override
    public UserResponse getUserBy(Long id) {
        return null;
    }

    @Override
    public UserResponse getUserBy(String username) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
