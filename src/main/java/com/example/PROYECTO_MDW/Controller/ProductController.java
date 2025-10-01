package com.example.PROYECTO_MDW.Controller;

import com.example.PROYECTO_MDW.Productos.Product;
import com.example.PROYECTO_MDW.Productos.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    private static final String CART_SESSION_KEY = "carrito";
    
    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@RequestParam int productId,
                                    @RequestParam int quantity,
                                    HttpSession session,
                                    RedirectAttributes ra) {
        
        Product product = productService.getProductById(productId);
        if (product == null || quantity <= 0) {
            ra.addFlashAttribute("errorStock", "Producto no válido.");
            return "redirect:/productos";
        }
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) { cart = new HashMap<>(); }
        
        int currentQuantityInCart = cart.getOrDefault(productId, 0);
        int requestedTotal = currentQuantityInCart + quantity;
        
        // verifica la cantidd
        if (requestedTotal > product.getStock()) {
            ra.addFlashAttribute("errorStock", "Error. Solo quedan " + product.getStock() + " unidades de " + product.getName() + ".");
            return "redirect:/productos";
        }
        
        cart.put(productId, requestedTotal);
        session.setAttribute(CART_SESSION_KEY, cart);
        
        return "redirect:/productos";
    }
    
    //modificar cantidad en el carrito pero en el offcanvas
    @PostMapping("/carrito/actualizar")
    public String actualizarCarrito(@RequestParam int productId,
                                    @RequestParam int newQuantity,
                                    HttpSession session,
                                    RedirectAttributes ra) {
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_SESSION_KEY);
        Product product = productService.getProductById(productId);

        if (cart == null || product == null) {
            return "redirect:/productos";
        }
        if (newQuantity <= 0) {
            cart.remove(productId);
        } else {
            if (newQuantity > product.getStock()) {
                ra.addFlashAttribute("errorStock", "Stock máximo para " + product.getName() + " es " + product.getStock() + ".");
                cart.put(productId, product.getStock());
            } else {
                cart.put(productId, newQuantity);
            }
        }
        
        session.setAttribute(CART_SESSION_KEY, cart);
        return "redirect:/productos";
    }
}