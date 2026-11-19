package com.boosterltd.iptbank.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boosterltd.iptbank.helper.ResponseStatusExceptionMessage;
import com.boosterltd.iptbank.model.entity.AdminEntity;
import com.boosterltd.iptbank.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {
    private final AdminRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AdminEntity entity = repository.findByUsername(username).orElseThrow(
            () -> { return new UsernameNotFoundException(
                ResponseStatusExceptionMessage.NOT_FOUND.getMessage().formatted(
                    "admin", "username", username
                )
            ); }
        );

        return User.builder()
            .username(entity.getUsername())
            .password(entity.getHashedPassword())
            .roles("ADMIN") // Same as {@code .authorities("ROLE_ADMIN");}
            .build();
    }
}
