package com.coralpay.coralsee.services.user_management_service;

import com.coralpay.coralsee.dtos.requests.SignupRequest;
import com.coralpay.coralsee.dtos.responses.UserResponse;
import com.coralpay.coralsee.entities.User;
import com.coralpay.coralsee.enums.Authority;
import com.coralpay.coralsee.exceptions.RegistrationException.UserAlreadyExistsException;
import com.coralpay.coralsee.repositories.UserRepository;
import com.coralpay.coralsee.security.entities.SecureUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.coralpay.coralsee.utils.Constants.USER_ALREADY_EXIST;
import static com.coralpay.coralsee.utils.Constants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse getUserBy(Long id) {
        return null;
    }

    @Override
    public UserResponse getUserBy(String emailAddress) {
        return modelMapper.map(getUserByUsername(emailAddress), UserResponse.class);
    }

    @Override
    public UserResponse createUserAccount(SignupRequest signupRequest) throws UserAlreadyExistsException {
        if (userRepository.findByEmailAddress(signupRequest.getEmailAddress()).isPresent()) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXIST);
        }

        User user = new User();
        user.setEmailAddress(signupRequest.getEmailAddress());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setAuthorities(Set.of(Authority.USER));
        user.setFullName(signupRequest.getFullName());
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecureUser(getUserByUsername(username));
    }

    private User getUserByUsername(String emailAddress){
        return userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }
}
