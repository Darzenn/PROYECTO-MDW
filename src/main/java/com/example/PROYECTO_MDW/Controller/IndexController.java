package com.example.PROYECTO_MDW.Controller;

import org.springframework.ui.Model;

import com.example.PROYECTO_MDW.Productos.Product;
import com.example.PROYECTO_MDW.Productos.ProductService;
import com.example.PROYECTO_MDW.Usuarios.User;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private ProductService productService;
    private static final String CART_SESSION_KEY = "carrito";

    @GetMapping("/")
    public String mostrarIndex() {
        return "index";
    }

    @GetMapping("/productos")
    public String mostrarProductos(Model model, HttpSession session) {
        model.addAttribute("allProducts", productService.getAllProducts());
        Map<String, Object> cartData = getCartDetails(session);
        model.addAllAttributes(cartData);
        return "productos";
    }

    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("user", new User());
        return "registerzoun";
    }

    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model) {
        Map<String, Object> cartData = getCartDetails(session);
        model.addAllAttributes(cartData);
        return "carrito";
    }

    @GetMapping("/zonapago")
    public String mostrarZonaPago(HttpSession session, Model model) {
        Map<String, Object> cartData = getCartDetails(session);
        model.addAllAttributes(cartData);
        return "zonapago";
    }

    private Map<String, Object> getCartDetails(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_SESSION_KEY);

        List<Map<String, Object>> cartDetails = new ArrayList<>();
        double total = 0.0;

        if (cart != null) {
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                Product product = productService.getProductById(entry.getKey());
                if (product != null) {
                    int quantity = entry.getValue();
                    double subtotal = product.getPrice() * quantity;
                    total += subtotal;

                    Map<String, Object> detail = new HashMap<>();
                    detail.put("product", product);
                    detail.put("quantity", quantity);
                    detail.put("subtotal", subtotal);
                    cartDetails.add(detail);
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("cartDetails", cartDetails);
        result.put("cartTotal", total);
        return result;
    }
}
