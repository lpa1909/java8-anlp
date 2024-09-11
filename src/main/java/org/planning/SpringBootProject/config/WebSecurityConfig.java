package org.planning.SpringBootProject.config;

import org.planning.SpringBootProject.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
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
    public SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        return new DefaultWebSecurityExpressionHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Consider enabling CSRF protection in a real application
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo")
                        .hasAnyRole("EMPLOYEE", "MANAGER")
                        .requestMatchers("/admin/product")
                        .hasRole("MANAGER")
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403")
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/j_spring_security_check")
                        .defaultSuccessUrl("/admin/accountInfo")
                        .failureUrl("/admin/login?error=true")
                        .permitAll() // Allow unauthenticated access to the login page
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/")
                        .permitAll() // Allow unauthenticated access to the logout URL
                );

        return http.build();
    }
}