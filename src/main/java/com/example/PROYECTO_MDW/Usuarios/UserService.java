package com.example.PROYECTO_MDW.Usuarios;
import java.util.ArrayList;


import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {
    
        private final List<User> listaUsuarios = new ArrayList<>();

    public UserService() {
        listaUsuarios.add(new User("Admin", "admin@prueba.com", "123"));
        listaUsuarios.add(new User("user", "usuario@test.com", "456"));
    }

    
    public boolean regirstrarUsuario(User usuario) {
        
        if (usuario.getNombre() == null || usuario.getNombre().isBlank() ||
            usuario.getEmail() == null || usuario.getEmail().isBlank() ||
            usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            System.out.println("Intento de registro fallido: Campos incompletos.");
            return false;
        }
        
        
        boolean emailExistente = listaUsuarios.stream().anyMatch(u -> u.getEmail().equals(usuario.getEmail()));
        
        if (emailExistente) {
            System.out.println("Intento de registro fallido: el correo " + usuario.getEmail() + " ya existe."); 
            return false;
        } else {
            listaUsuarios.add(usuario);
            System.out.println("Usuario registrado exitosamente: " + usuario.getEmail());
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
