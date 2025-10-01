package com.example.PROYECTO_MDW.Segurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers("/", "/index", "/registerzoun", "/productos", "/register", "/login", "/logout", "/css/**", "/js/**", "/carrito/agregar", "/carrito/eliminar", "/carrito/actualizar", "/zonapago").permitAll()
                // Cualquier otra requiere login
                .anyRequest().authenticated()
)
                .formLogin(form -> form.disable())
                .logout(logout -> logout
                .logoutUrl("/logout")
                .permitAll());

        return http.build();
}
}
