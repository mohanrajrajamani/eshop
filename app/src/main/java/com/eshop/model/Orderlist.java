package com.eshop.model;

import java.util.ArrayList;

/**
 * Created by Admin on 07-11-2017.
 */

public class Orderlist extends BasicResponse {

    ArrayList<Orderdetail> orderdetails;

    public ArrayList<Orderdetail> getOrderdetails() {
        return orderdetails;
    }
}
