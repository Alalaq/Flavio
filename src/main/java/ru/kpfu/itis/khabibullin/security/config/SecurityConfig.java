package ru.kpfu.itis.khabibullin.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsServiceImpl;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/css/**", "/js/**", "/img/**", "/static/**");
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http
                        .csrf().disable()
                        .authorizeHttpRequests()
                        //.anyRequest().permitAll()
                        .requestMatchers("/homepage", "/login", "/registration", "/restaurants").permitAll()
                        .requestMatchers("/profile/**").authenticated()
                        .requestMatchers("/users").hasAuthority("ADMIN")
                        .requestMatchers("/addresses/**").permitAll()
                        .requestMatchers("/getCurrentUserId").permitAll()
                        .requestMatchers("/logout").authenticated()
                        .and().formLogin().loginPage("/login").defaultSuccessUrl("/homepage")
                        .and().logout().logoutUrl("/myLogout")
                        .and().build();
    }

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

}