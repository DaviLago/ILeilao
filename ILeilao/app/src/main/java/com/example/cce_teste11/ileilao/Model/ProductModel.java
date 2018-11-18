package com.example.cce_teste11.ileilao.Model;

public class ProductModel {

    private Long id;
    private String prod_name;
    private String prod_description;
    private Boolean status;
    private UserModel seller;

    public ProductModel(Long id, String prod_name, String prod_description, Boolean status, UserModel seller) {
        this.id = id;
        this.prod_name = prod_name;
        this.prod_description = prod_description;
        this.status = status;
        this.seller = seller;
    }

    public ProductModel(String prod_name, String prod_description, Boolean status, UserModel seller) {
        this.prod_name = prod_name;
        this.prod_description = prod_description;
        this.status = status;
        this.seller = seller;
    }

    public ProductModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_description() {
        return prod_description;
    }

    public void setProd_description(String prod_description) {
        this.prod_description = prod_description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserModel getSeller() {
        return seller;
    }

    public void setSeller(UserModel seller) {
        this.seller = seller;
    }

}
