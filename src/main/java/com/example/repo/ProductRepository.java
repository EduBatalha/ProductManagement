package com.example.repo;


import com.example.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.hash = :productHash")
    Product findByHash(@Param("productHash") UUID productHash);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.hash = :productHash")
    void deleteByHash(@Param("productHash") UUID productHash);

    @Transactional
    default Product saveProduct(Product product) {
        return saveAndFlush(product);
    }

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.name = :name, p.description = :description, p.ean13 = :ean13, " +
            "p.active = :active, p.minStock = :minStock, p.price = :price, p.quantity = :quantity, p.dtUpdate = CURRENT_TIMESTAMP " +
            "WHERE p.hash = :productHash")
    void updateProduct(@Param("productHash") UUID productHash,
                       @Param("name") String name,
                       @Param("description") String description,
                       @Param("ean13") String ean13,
                       @Param("active") boolean active,
                       @Param("minStock") int minStock,
                       @Param("price") double price,
                       @Param("quantity") int quantity);


}

