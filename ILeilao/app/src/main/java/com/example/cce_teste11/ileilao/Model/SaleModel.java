package com.example.cce_teste11.ileilao.Model;

public class SaleModel {

    Long id;
    Double min_value;
    String password;
    Boolean status;
    ProductModel product;

    public SaleModel(Long id, Double min_value, String password, Boolean status, ProductModel product) {
        this.id = id;
        this.min_value = min_value;
        this.password = password;
        this.status = status;
        this.product = product;
    }

    public SaleModel(Double min_value, String password, Boolean status, ProductModel product) {
        this.min_value = min_value;
        this.password = password;
        this.status = status;
        this.product = product;
    }

    public SaleModel(ProductModel product) {
        this.product = product;
    }

    public SaleModel(Long id, Double min_value, String password, Boolean status) {
        this.id = id;
        this.min_value = min_value;
        this.password = password;
        this.status = status;
    }

    public SaleModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Double getMin_value() {
        return min_value;
    }

    public void setMin_value(Double min_value) {
        this.min_value = min_value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
