package com.SMB.bookManagementSystem.TokenBasedSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) ->
                requests
                        .requestMatchers("/books/create", "/books/update/**", "/books/delete/**", "/books").hasRole("ADMIN")
                        .requestMatchers("/books/**").hasRole("USER")
                        .anyRequest().authenticated());

        http.sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.ALWAYS));

        http.formLogin(withDefaults());
        http.csrf(csrf -> csrf.disable());
//        http.csrf(withDefaults());
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder().encode("password1"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN")
                .build();

        JdbcUserDetailsManager userDetailsManager
                = new JdbcUserDetailsManager(dataSource);

        if(!userDetailsManager.userExists("user1")){
        userDetailsManager.createUser(user1);
        }
        if(!userDetailsManager.userExists("admin")) {
            userDetailsManager.createUser(admin);
        }

        return userDetailsManager;
 //       return new InMemoryUserDetailsManager(user1, admin);
    }
        @Bean
        PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        }
}
