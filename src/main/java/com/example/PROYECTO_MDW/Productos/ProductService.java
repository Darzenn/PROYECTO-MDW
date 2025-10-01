package com.example.PROYECTO_MDW.Productos;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private static final List<Product> products = Arrays.asList(
        new Product(101, "Reloj Deportivo", 50.00, 15),
        new Product(102, "Reloj Clásico", 80.00, 5),
        new Product(103, "Reloj Inteligente", 150.00, 25),
        new Product(104, "Reloj con Correa de Acero", 120.00, 10),
        new Product(105, "Reloj de Muñeca", 150.00, 8),
        new Product(106, "Reloj Digital", 300.00, 2),
        new Product(107, "Reloj para Damas", 700.00, 20),
        new Product(108, "Reloj Deportivo Femenino", 600.00, 12),
        new Product(109, "Reloj Porta Auriculares", 849.00, 4)
    );

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(int id) {
        return products.stream()
            .filter(p -> p.getId() == id)
            .findFirst()
            .orElse(null);
    }
}
