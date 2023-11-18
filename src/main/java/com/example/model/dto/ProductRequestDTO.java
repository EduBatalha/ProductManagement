package com.example.model.dto;

public class ProductRequestDTO {

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

    // Construtor com parâmetros
    public ProductRequestDTO(String name, String description, String ean13, boolean active,
                             int minStock, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.ean13 = ean13;
        this.active = active;
        this.minStock = minStock;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters e Setters

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

