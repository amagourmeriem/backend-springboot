package com.example.marsamaroc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/engins/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/engins/uploads/**","/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/engins/**", "/categories_engins/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/engins/**","/categories_engins/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/demandes/**","/engins/update/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/demandes/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/demandes/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/demandes/**").authenticated()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
