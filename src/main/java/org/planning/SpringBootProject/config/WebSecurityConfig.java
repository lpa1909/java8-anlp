package org.planning.SpringBootProject.config;

import org.planning.SpringBootProject.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Autowired
    @Lazy
    UserDetailsServiceImpl userDetailsService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());


        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/**").hasAnyAuthority("ROLE_MANAGER")
                .requestMatchers("/user/**").hasRole("EMPLOYEE")
                .anyRequest().authenticated()
        );


        return http.getOrBuild();
    }
}