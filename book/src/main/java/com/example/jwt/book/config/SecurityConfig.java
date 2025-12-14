package com.example.jwt.book.config;

import com.example.jwt.book.Utils.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    AuthenticationProvider authenticationProvider;
    @Autowired
    JwtAuthFilter authFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
         http.csrf(csrf-> csrf.disable())
                 .authorizeHttpRequests(auth->
                         auth.requestMatchers("/reviews/**").hasAnyAuthority("USER","ADMIN")
                                 .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                 .requestMatchers("/auth/**","/books/**","/h2-console").permitAll()
                                 .anyRequest().authenticated()
                         )
                 .headers(header->header.frameOptions(frame->frame.sameOrigin()))
                 .authenticationProvider(authenticationProvider)
                 .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }
}
