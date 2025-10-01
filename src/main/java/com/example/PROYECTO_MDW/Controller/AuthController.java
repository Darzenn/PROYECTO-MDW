package com.example.PROYECTO_MDW.Controller;
import com.example.PROYECTO_MDW.Usuarios.User;
import com.example.PROYECTO_MDW.Usuarios.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute User user, RedirectAttributes ra) {
        
        boolean registroExitoso = userService.registrarUsuario(user);
        
        if (registroExitoso) {
            ra.addFlashAttribute("registroExitosoFlag", true);
            return "redirect:/";
        } else {
            String errorMsg;
            if (user.getNombre() == null || user.getNombre().isBlank() ||
                user.getEmail() == null || user.getEmail().isBlank() ||
                user.getPassword() == null || user.getPassword().isBlank()) {
                    errorMsg = "Todos los campos son obligatorios. Por favor, llénalos.";
            } else {
                    errorMsg = "El correo electrónico ya está registrado. Intenta con otro.";
                    
            }
            ra.addFlashAttribute("errorRegistro", errorMsg);
            ra.addFlashAttribute("user", user);
            return "redirect:/register";
        }
    }

    // 2. PROCESAR EL LOGIN
    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("errorLogin", "Por favor, ingresa tu correo y contraseña.");
            System.out.println("INGRESAR CORREO Y CONTRASEÑA");
            return "index";
        }
        User usuario = userService.login(email, password);
        if (usuario != null) {
            session.setAttribute("usuarioLogeado", usuario);
            return "redirect:/";
        } else {
            model.addAttribute("errorLogin", "Correo o contraseña incorrecta.");
            System.out.println("CORREO O CONTRASEÑA INCORRECTA");
            return "index";
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}