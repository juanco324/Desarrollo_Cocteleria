package cocteleriaBackendWeb.backendCocteleriaWeb.config;

import cocteleriaBackendWeb.backendCocteleriaWeb.seguridad.JwtFilter;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/jefe/**").hasRole("JEFE")
                .requestMatchers("/privado/**").hasAnyRole("ADMIN", "JEFE")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}