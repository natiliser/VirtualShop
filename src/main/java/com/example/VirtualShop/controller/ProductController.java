package com.example.VirtualShop.controller;

import com.example.VirtualShop.dto.ProductIn;
import com.example.VirtualShop.model.Product;
import com.example.VirtualShop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneProduct(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductIn productIn) {
        Product product = productIn.toProduct();
        product = productService.saveProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductIn productIn) {
        Optional<Product> dbProduct = productService.getProductById(id);

        if (dbProduct.isPresent()) {
            Product existingProduct = dbProduct.get();
            productIn.updateProduct(existingProduct);
            Product updatedProduct = productService.saveProduct(existingProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }

        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> dbProduct = productService.getProductById(id);

        if (dbProduct.isPresent()) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/sell")
    public ResponseEntity<?> sellProduct(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            Optional<Product> updatedProduct = productService.sellProduct(id, quantity);

            if (updatedProduct.isPresent()) {
                return new ResponseEntity<>(updatedProduct.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}