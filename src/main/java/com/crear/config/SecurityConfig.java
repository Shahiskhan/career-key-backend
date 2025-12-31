package com.crear.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.crear.security.JwtAuthenticationFilter;
// import com.crear.security.oauth2.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Value("${app.auth.failure-redirect}")
    private String failureRedirectURL;

    private Logger logger = Logger.getLogger(SecurityConfig.class.getName());

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())

                .cors(Customizer.withDefaults())

                // 3. Stateless Session
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()

                        .requestMatchers("/api/v1/auth/**").permitAll()

                        .requestMatchers("/api/v1/degree-requests/**").permitAll()

                        .requestMatchers("/api/v1/student/**").permitAll()
                        .requestMatchers("/api/v1/**").permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .anyRequest().authenticated()

                )

                .logout(AbstractHttpConfigurer::disable)

                // Error handling (401 Unauthorized)
                .exceptionHandling(eh -> eh.authenticationEntryPoint((req, resp, e) -> {
                    resp.setStatus(401);
                    resp.setContentType("application/json");

                    String message = (String) req.getAttribute("exception");
                    ObjectMapper om = new ObjectMapper();

                    if ("token_expired".equals(message)) {
                        resp.getWriter().println(om.writeValueAsString(
                                Map.of("error", "token_expired", "message", "Aapka session khatam ho chuka hai")));
                    } else if ("invalid_token".equals(message)) {
                        resp.getWriter().println(om.writeValueAsString(
                                Map.of("error", "invalid_token", "message", "Token durust nahi hai")));
                    } else {
                        resp.getWriter().println(
                                om.writeValueAsString(Map.of("error", "unauthorized", "message", e.getMessage())));
                    }
                }))

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt with default strength is suitable; increase log rounds in
        // high-security contexts
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.allowed-origins}") String allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins));

        // For production, restrict to your frontends
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE",
                "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type",
                "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
