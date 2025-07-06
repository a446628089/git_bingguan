package com.example.hotelmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(
                    "/", "/index.html", "/login.html", "/register.html", "/profile.html",
                    "/css/**", "/js/**", "/images/**", "/static/**"
                ).permitAll()
                .antMatchers("/api/customers/register").permitAll()
                .antMatchers("/api/rooms/**", "/api/comments/**").permitAll()
                .antMatchers("/api/employees/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .antMatchers("/api/customers/**").hasAnyAuthority("CUSTOMER", "ADMIN", "EMPLOYEE")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler())
                .failureHandler(myAuthenticationFailureHandler())
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                .permitAll();
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .filter(r -> r.equals("ADMIN") || r.equals("EMPLOYEE") || r.equals("CUSTOMER"))
                .findFirst().orElse("CUSTOMER");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"role\":\"" + role + "\"}");
        };
    }

    @Bean
    public AuthenticationFailureHandler myAuthenticationFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"账号或密码错误\"}");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
} 