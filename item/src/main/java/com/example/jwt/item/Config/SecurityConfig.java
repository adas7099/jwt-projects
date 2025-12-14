package com.example.jwt.item.Config;

import com.example.jwt.item.utils.JwtAuthFilter;
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
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthFilter authFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->
                        auth.requestMatchers(HttpMethod.GET,"/items").hasAnyAuthority("USER","ADMIN")
                                .requestMatchers(HttpMethod.POST,"/items").hasAnyAuthority("USER","ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/items/**").hasAnyAuthority("USER","ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/items/**").hasAuthority("ADMIN")
                                .requestMatchers("/auth/**","/h2-console").permitAll()
                                .anyRequest().authenticated()
                        )
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();


    }
}
