package com.example.service;




import com.example.model.dto.ProductRequestDTO;
import com.example.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.repo.ProductRepository;

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

    public boolean deleteProduct (UUID hash) {
        productRep.deleteByHash(hash);

        return productRep.findByHash(hash) != null;
    }

    public void saveProduct(Product product) {
        // Lógica para salvar o produto, por exemplo:
        product.setDtCreate(new Date());
        product.setHash(UUID.randomUUID());
        product.setDtCreate(new Date());

        productRep.saveProduct(product);;
    }
}
