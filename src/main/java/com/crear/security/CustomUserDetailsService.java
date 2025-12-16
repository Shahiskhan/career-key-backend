package com.crear.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.crear.auth.model.User;
import com.crear.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository users;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return users.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with this email id: " + email));
        }
}
