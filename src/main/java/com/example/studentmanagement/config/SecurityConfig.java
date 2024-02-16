package com.example.studentmanagement.config;

import com.example.studentmanagement.entity.UserType;
import com.example.studentmanagement.security.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/loginPage").permitAll()
                .requestMatchers("/user/register").permitAll()
                .requestMatchers("/lessons/add").hasAnyAuthority(UserType.TEACHER.name(),UserType.STUDENT.name())
                .requestMatchers("/students/add").hasAnyAuthority(UserType.TEACHER.name())
                .requestMatchers("/lessons").hasAnyAuthority(UserType.TEACHER.name(), UserType.STUDENT.name())
                .requestMatchers("/teachers").hasAnyAuthority(UserType.TEACHER.name(),UserType.STUDENT.name())
                .requestMatchers("/teachers/update").hasAnyAuthority(UserType.TEACHER.name())
                .requestMatchers("/students").hasAnyAuthority(UserType.TEACHER.name(),UserType.STUDENT.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/loginSuccess",true)
                .and()
                .logout()
                .logoutSuccessUrl("/");
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}