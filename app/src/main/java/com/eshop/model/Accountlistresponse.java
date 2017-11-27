package com.eshop.model;

import java.util.ArrayList;

/**
 * Created by Admin on 07-11-2017.
 */

public class Accountlistresponse extends BasicResponse {


    ArrayList<Accountdetailresponse> accountdetails;

    public ArrayList<Accountdetailresponse> getAccountdetails() {
        return accountdetails;
    }
}
