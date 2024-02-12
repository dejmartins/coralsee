package com.coralpay.coralsee.dtos.responses;

import com.coralpay.coralsee.enums.Authority;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String emailAddress;
    private Set<Authority> authorities;
}
