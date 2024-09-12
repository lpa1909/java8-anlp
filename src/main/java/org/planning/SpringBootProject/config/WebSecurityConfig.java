package org.planning.SpringBootProject.config;

import org.planning.SpringBootProject.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login", "/admin/logout", "/403")
                        .permitAll() // Cho phép truy cập mà không cần xác thực
                        .requestMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo")
                        .hasAnyRole("EMPLOYEE", "MANAGER")
                        .requestMatchers("/admin/product")
                        .hasRole("MANAGER")
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e.accessDeniedPage("/403"))
                .formLogin(login -> login
                        .loginProcessingUrl("/j_spring_security_check")
                        .loginPage("/admin/login")
                        .defaultSuccessUrl("/admin/accountInfo")
                        .failureUrl("/admin/login?error=true")
                        .usernameParameter("userName")
                        .passwordParameter("password"))
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}