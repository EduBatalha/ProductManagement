package com.example.repo;


import com.example.model.entity.Product;
import com.example.model.dto.ProductRequestDTO;
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
}

