package com.example.service;

import com.example.model.dto.ProductRequestDTO;
import com.example.model.entity.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.repo.ProductRepository;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRep;

    public List<Product> getAllProducts() {

        return new ArrayList<>(productRep.findAll());
    }

    public Product getProductByHash(UUID hash) {
        return productRep.findByHash(hash);
    }

    public ResponseEntity<String> updateProduct(UUID productHash, ProductRequestDTO updatedProductData) {
        System.out.println("Update Product Service - Hash: " + productHash);
        System.out.println("Updated Product Data: " + updatedProductData.toString());
        Product existingProduct = productRep.findByHash(productHash);

        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        // Adicione validações adicionais aqui, se necessário

        existingProduct.setName(updatedProductData.getName());
        existingProduct.setDescription(updatedProductData.getDescription());
        existingProduct.setEan13(updatedProductData.getEan13());
        existingProduct.setActive(updatedProductData.isActive());
        existingProduct.setMinStock(updatedProductData.getMinStock());
        existingProduct.setPrice(updatedProductData.getPrice());
        existingProduct.setQuantity(updatedProductData.getQuantity());
        // Atualize outros campos conforme necessário

        productRep.updateProduct(
                productHash,
                updatedProductData.getName(),
                updatedProductData.getDescription(),
                updatedProductData.getEan13(),
                updatedProductData.isActive(),
                updatedProductData.getMinStock(),
                updatedProductData.getPrice(),
                updatedProductData.getQuantity()
        );

        return ResponseEntity.ok("Produto atualizado com sucesso");
    }

    public boolean deleteProduct(UUID hash) {
        try {
            productRep.deleteByHash(hash);
            return true; // Produto excluído com sucesso
        } catch (Exception e) {
            // Log ou tratamento de exceção, dependendo dos requisitos
            return false; // Erro ao excluir produto
        }
    }


    public void saveProduct(Product product) {
        try {
            product.setDtCreate(new Date());
            product.setHash(UUID.randomUUID());

            productRep.saveProduct(product);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) e.getCause();

                for (ConstraintViolation<?> violation : cause.getConstraintViolations()) {
                    if (violation.getPropertyPath().toString().equals("name")) {
                        throw new ProductValidationException("Já existe um produto com o mesmo nome.");
                    } else if (violation.getPropertyPath().toString().equals("ean13")) {
                        throw new ProductValidationException("Já existe um produto com o mesmo EAN-13.");
                    }
                }
            }
            throw e; // Re-throw the exception if it's not a constraint violation related to name or ean13
        }
    }

}

