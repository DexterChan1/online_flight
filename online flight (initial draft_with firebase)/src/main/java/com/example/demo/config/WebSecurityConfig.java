package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers(new AntPathRequestMatcher("/users/signup")) .permitAll()
                        .anyRequest().authenticated()
        );

        http.oauth2ResourceServer().jwt();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer configure() throws Exception {
        return web -> web.ignoring().requestMatchers(toH2Console());
    }


}
