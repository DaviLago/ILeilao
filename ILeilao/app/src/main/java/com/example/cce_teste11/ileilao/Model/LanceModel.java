package com.example.cce_teste11.ileilao.Model;

public class LanceModel {
    private Long id;
    private Double value;
    private ProductModel product;
    private UserModel user;

    public LanceModel(Long id, Double value, ProductModel product, UserModel user) {
        this.id = id;
        this.value = value;
        this.product = product;
        this.user = user;
    }

    public LanceModel(Double value, ProductModel product, UserModel user) {
        this.value = value;
        this.product = product;
        this.user = user;
    }

    public LanceModel(Long id, Double value, UserModel user) {
        this.id = id;
        this.value = value;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
