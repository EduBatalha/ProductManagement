package com.example.model.dto;

import com.example.model.entity.Product;

import java.util.UUID;

public class ProductRequestDTO {

    private UUID hash;
    private String name;
    private String description;
    private String ean13;
    private boolean active;
    private int minStock;
    private double price;
    private int quantity;

    // Construtor padrão
    public ProductRequestDTO() {
        // Pode adicionar lógica de inicialização aqui, se necessário
    }

    public Product toEntity() {
        Product product = new Product();
        product.setHash(this.getHash());
        product.setName(this.getName());
        product.setDescription(this.getDescription());
        product.setEan13(this.getEan13());
        product.setPrice(this.getPrice());
        product.setQuantity(this.getQuantity());
        product.setMinStock(this.getMinStock());
        product.setActive(this.isActive());
        return product;
    }

    public ProductRequestDTO fromEntity(Product product) {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setHash(product.getHash());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setEan13(product.getEan13());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setMinStock(this.getMinStock());
        dto.setActive(this.isActive());
        return dto;
    }

    // Construtor com parâmetros
    public ProductRequestDTO(UUID hash,String name, String description, String ean13, boolean active,
                             int minStock, double price, int quantity) {
        this.hash = hash;
        this.name = name;
        this.description = description;
        this.ean13 = ean13;
        this.active = active;
        this.minStock = minStock;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters e Setters

    public UUID getHash() {
        return hash;
    }

    public void setHash(UUID hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

