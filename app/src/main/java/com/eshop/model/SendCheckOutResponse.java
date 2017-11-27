package com.eshop.model;

/**
 * Created by Admin on 09-11-2017.
 */

public class SendCheckOutResponse {


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    private String account_id;

    public SendCheckOutResponse(String account_id, String total, String email, String product_name) {
        this.account_id = account_id;
        this.total = total;
        this.email = email;
        this.product_name = product_name;
    }

    private String total;
    private String email;
    private String product_name;

}
