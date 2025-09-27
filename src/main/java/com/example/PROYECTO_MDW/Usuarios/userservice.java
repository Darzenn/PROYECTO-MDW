package com.example.PROYECTO_MDW.Usuarios;

import java.util.ArrayList;

public class userservice {
    
    private ArrayList<user> listaUsuarios;

    public void regirstrarUsuario(user usuario) {
        if (listaUsuarios == null) {
            listaUsuarios = new ArrayList<>();
        }
        listaUsuarios.add(usuario);
    }

    public user login(String email, String password) {
        if (listaUsuarios != null) {
            for (user usuario : listaUsuarios) {
                if (usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                    return usuario;
                }
            }
        }
        return null; // Retorna null si no se encuentra el usuario
    }


}
