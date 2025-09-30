package com.example.PROYECTO_MDW.Usuarios;
import java.util.ArrayList;


import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {
    
    private final List<User> listaUsuarios = new ArrayList<>();

    public UserService() {
        listaUsuarios.add(new User("admin", "admin@prueba.com", "123"));
        listaUsuarios.add(new User("user", "usuario@prueba.com", "456"));
    }

    public boolean registrarUsuario(User usuario) {
        
        if (usuario.getNombre() == null || usuario.getNombre().isBlank() ||
            usuario.getEmail() == null || usuario.getEmail().isBlank() ||
            usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            return false;
        }
        boolean emailExistente = listaUsuarios.stream().anyMatch(u -> u.getEmail().equals(usuario.getEmail()));
        if (emailExistente) {
            System.out.println("El correo electrónico ya está registrado.");
            return false;
        } else {
            listaUsuarios.add(usuario);
            System.out.println("Usuario registrado exitosamente.");
            return true;
        }
    }

    public User login(String email, String password) {
        return listaUsuarios.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
