package com.example.PROYECTO_MDW.Segurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.PROYECTO_MDW.Usuarios.Usuario;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Simulamos una "base de datos" con ArrayList
    private final List<Usuario> usuarios = new ArrayList<>();

    // Método para registrar usuarios
    public void addUser(Usuario nuevoUsuario) {
        usuarios.add(nuevoUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario por email (login con email)
        Usuario encontrado = usuarios.stream()
                .filter(u -> u.getEmail().equals(username)) // login con email
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Devolvemos el nombre como "username" para mostrarlo en el navbar
        return User.withUsername(encontrado.getNombre()) // 👈 se mostrará en el navbar
                .password("{noop}" + encontrado.getPassword()) // {noop} = sin encriptar
                .roles("USER")
                .build();
    }
}
