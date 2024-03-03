package com.Jeeva.UserService.model;


import com.Jeeva.Utils.UserIdentifier;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String name;

    @Column(length = 30, unique = true)
    private String email;

    @Column(length = 15, unique = true, nullable = false)
    private String phoneNo;

    private  String address;

    private String password;

    private String authority;

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    private UserIdentifier userIdentifier;

    private String userIdentifierValue;

    private String dob;

    @CreationTimestamp
    private Date CreatedOn;

    @UpdateTimestamp
    private Date updatedOn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authority.split(",")).map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return phoneNo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
