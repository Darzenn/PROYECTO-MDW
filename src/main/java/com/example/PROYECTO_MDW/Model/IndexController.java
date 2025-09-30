package com.example.PROYECTO_MDW.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.PROYECTO_MDW.Usuarios.Usuario;
import com.example.PROYECTO_MDW.Segurity.CustomUserDetailsService;

@Controller
public class IndexController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Página principal
    @GetMapping("/")
    public String mostrarIndex() {
        return "index"; // index.html
    }

    // Página de productos
    @GetMapping("/productos")
    public String mostrarProductos() {
        return "productos"; // productos.html
    }

    // Página de registro
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("user", new Usuario()); // 👈 ahora usa Usuario
        return "register"; // tu plantilla de registro
    }

    // Procesar registro
    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute("user") Usuario usuario, Model model) {
        customUserDetailsService.addUser(usuario);
        return "redirect:/"; // después de registrar vuelve al index
    }
}
