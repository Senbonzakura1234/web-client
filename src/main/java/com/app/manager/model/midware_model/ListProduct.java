package com.app.manager.model.midware_model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class ListProduct {
    @JacksonXmlProperty(localName = "item")
    @JacksonXmlCData
//    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ProductModel> productModels;

    public ListProduct() {
    }

    public ListProduct(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }
}
