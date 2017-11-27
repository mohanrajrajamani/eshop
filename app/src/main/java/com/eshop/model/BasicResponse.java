package com.eshop.model;

import java.util.ArrayList;

/**
 * Created by Admin on 04-08-2017.
 */

public class BasicResponse {

    private int success;
    private String message;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String email;
    private String phone;

    private ArrayList<Catagorey>catagories;

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

    private ArrayList<Product>product;

    public ArrayList<Catagorey> getCatagories() {
        return catagories;
    }

    public void setCatagories(ArrayList<Catagorey> catagories) {
        this.catagories = catagories;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
