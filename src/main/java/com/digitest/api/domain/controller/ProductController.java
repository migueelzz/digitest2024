package com.digitest.api.domain.controller;

import com.digitest.api.domain.dto.ProductDTO;
import com.digitest.api.domain.model.Product;
import com.digitest.api.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.findAll()
                .stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getPrice()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(new ProductDTO(product.getId(), product.getName(), product.getPrice())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        Product newProduct = new Product();
        newProduct.setName(product.name());
        newProduct.setPrice(product.price());

        Product savedProduct = productService.save(newProduct);
        ProductDTO savedProductDTO = new ProductDTO(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
        return productService.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(product.name());
                    existingProduct.setPrice(product.price());

                    Product updatedProduct = productService.save(existingProduct);
                    ProductDTO updatedProductDTO = new ProductDTO(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getPrice());

                    return ResponseEntity.ok(updatedProductDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.findById(id).isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

