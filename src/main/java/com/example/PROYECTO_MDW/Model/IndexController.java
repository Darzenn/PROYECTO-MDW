package com.example.PROYECTO_MDW.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @GetMapping("/")
    public String mostrarIndex() {
        return "index";
    }

    @GetMapping("/productos")
    public String mostrarProductos() {
        return "productos";
    }

    @GetMapping("/register")
    public String mostrarRegistro() {
        return "registerzoun";
    }

    
}
