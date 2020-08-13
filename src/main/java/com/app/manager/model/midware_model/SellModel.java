package com.app.manager.model.midware_model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class SellModel {
    @NotBlank
    private String id;
    @Min(value = 0)
    private int amount;

    public SellModel() {
    }

    public SellModel(@NotBlank String id, @Min(value = 0) int amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
