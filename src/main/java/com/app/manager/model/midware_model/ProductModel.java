package com.app.manager.model.midware_model;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductModel {
    private String id;
    @NotBlank(message = "must have name")
    private String name;
    @Min(value = 0, message = "price must greater than zero")
    private double price;
    @Min(value = 0, message = "amount must greater than zero")
    private int amount;

    public ProductModel() {
    }

    public ProductModel(@NotBlank(message = "must have name") String name, @Min(value = 0, message = "price must greater than zero") double price, @Min(value = 0, message = "amount must greater than zero") int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public ProductModel(String id, @NotBlank(message = "must have name") String name, @Min(value = 0, message = "price must greater than zero") double price, @Min(value = 0, message = "amount must greater than zero") int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
