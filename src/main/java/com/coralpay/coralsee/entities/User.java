package com.coralpay.coralsee.entities;

import com.coralpay.coralsee.enums.Authority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String fullName;
    private String password;
    private String phoneNumber;
    private String emailAddress;
    @Enumerated(EnumType.STRING)
    private Set<Authority> authorities;
}
