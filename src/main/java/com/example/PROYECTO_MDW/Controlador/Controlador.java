package com.example.PROYECTO_MDW.Controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controlador {
    
    @GetMapping("/index")
    public String mostrarIndex() {
        return "index";
    }

    @GetMapping("/productos")
    public String mostrarProductos() {
        return "productos";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    
}
