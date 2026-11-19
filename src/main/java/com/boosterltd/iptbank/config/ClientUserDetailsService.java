package com.boosterltd.iptbank.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.boosterltd.iptbank.helper.ResponseStatusExceptionMessage;
import com.boosterltd.iptbank.model.entity.ClientEntity;
import com.boosterltd.iptbank.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientUserDetailsService implements UserDetailsService {
    private final ClientRepository repository;

    @Override
    public UserDetails loadUserByUsername(String authIdInString) throws UsernameNotFoundException {
        final long authId;
        try {
            authId = Long.parseLong(authIdInString);
        }
        catch (NumberFormatException e) {
            throw new RuntimeException(
                "You haven't provided a number as clinet ID in the authentication part."
            );
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException(
                "You haven't provided anything as clinet ID in the URL."
            );
        }

        String[] requestUriParts = attributes
            .getRequest()
            .getRequestURI() // For example: "/api/private-client/123"
            .split("/");

        final long urlId;
        try {
            urlId = Long.parseLong(requestUriParts[requestUriParts.length - 1]);
        }
        catch (NumberFormatException e) {
            throw new RuntimeException(
                "You haven't provided a number as clinet ID in the URL."
            );
        }

        if (urlId != authId) {
            throw new UsernameNotFoundException(
                "Your given clinet ID in the URL and the authentication part don't match."
            );
        }

        ClientEntity entity = repository.findById(authId).orElseThrow(
            () -> { return new UsernameNotFoundException(
                ResponseStatusExceptionMessage.NOT_FOUND.getMessage().formatted(
                    "client", "ID", authId
                )
            ); }
        );

        return User.builder()
            .username(entity.getId().toString())
            .password(entity.getHashedPassword())
            .roles("CLIENT") // Same as {@code .authorities("ROLE_CLIENT");}
            .build();
    }
}
