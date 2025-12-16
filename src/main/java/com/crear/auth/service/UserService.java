package com.crear.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.crear.auth.model.Provider;
import com.crear.auth.model.User;
import com.crear.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUserIfNotExit(String providerId, String email, String username, String image, Provider provider) {

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            return User.builder()
                    .providerId(providerId)
                    .email(email)
                    .name(username)
                    .provider(provider)
                    .image(image)
                    .password("")
                    .enabled(true)
                    .build();
        });
        return userRepository.save(user);

    }

}
