package com.cavcav.Eventify.security;

import com.cavcav.Eventify.user.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthFilter filter;
    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthFilter filter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        // User Controller
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/getUserById/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/getAllUsers").hasRole("ADMIN")

                        // Follow Controller
                        .requestMatchers(HttpMethod.GET, "/api/v1/follow/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/follow/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/follow/**").authenticated()

                        // Event Controller
                        .requestMatchers(HttpMethod.GET, "/api/v1/event/getAllEvents").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/event/getEventById/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/event/getByOrganizerId/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/event/create").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/event/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/event/**").authenticated()

                        // Category Controller
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/createCategory").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasRole("ADMIN")

                        // Tüm diğer istekler
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsServiceImp);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }
}

