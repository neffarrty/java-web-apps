package se.semit.ykovtun.webappskyvlab4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.semit.ykovtun.webappskyvlab4.services.user.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserService service;

    public SecurityConfiguration(UserService service) {
        this.service = service;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return service::findByUsername;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(
                    "/hospital-departments/new",
                    "/hospital-departments/*/edit",
                    "/hospital-departments/*/patients/new",
                    "/hospital-departments/*/patients/*/edit",
                    "/patients/new",
                    "patients/*/edit"
                ).hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/hospital-departments/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredUrl("/auth/login?expired=true")
                .maxSessionsPreventsLogin(true)
            );

        return http.build();
    }
}
