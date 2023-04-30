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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

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
                        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringRequestMatchers("/addresses/**", "/restaurants/**", "/restaurants/filtered", "/api/users/**", "/verify-email/**",  "/error")
                        .and()
                        .authorizeHttpRequests()
                   //     .anyRequest().permitAll()
                        .requestMatchers("/homepage", "/login", "/registration", "/restaurants/**", "/addresses/**", "/getCurrentUserId", "/auth/check-authentication", "/verify-email/**", "/error").permitAll()
                        .requestMatchers("/profile/**", "/logout", "/restaurants/filtered").authenticated()
                        .requestMatchers("/users", "/api/users/*", "/admin-page").hasAuthority("ADMIN")
                        .and().formLogin().loginPage("/login").defaultSuccessUrl("/homepage")
                        .and().logout().logoutUrl("/myLogout")
                        .and().build();
    }

    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }
}