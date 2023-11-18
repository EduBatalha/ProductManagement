package com.example.model.dto;

import java.util.Date;

public class ProductReturnDTO {

    private int id;
    private String name;
    private String description;
    private String ean13;
    private String hash;
    private boolean active;
    private int minStock;
    private double price;
    private int quantity;
    private Date dtCreate;
    private Date dtUpdate;

    // Construtor padrão
    public ProductReturnDTO() {
        // Pode adicionar lógica de inicialização aqui, se necessário
    }

    // Construtor com parâmetros
    public ProductReturnDTO(int id, String name, String description, String ean13, String hash,
                            boolean active, int minStock, double price, int quantity,
                            Date dtCreate, Date dtUpdate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ean13 = ean13;
        this.hash = hash;
        this.active = active;
        this.minStock = minStock;
        this.price = price;
        this.quantity = quantity;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public Date getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Date dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Date getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Date dtUpdate) {
        this.dtUpdate = dtUpdate;
    }
}
