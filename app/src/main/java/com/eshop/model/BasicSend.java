package com.eshop.model;

import java.util.ArrayList;

/**
 * Created by Admin on 09-11-2017.
 */

public class BasicSend {

    public ArrayList<SendCheckOutResponse> getUpdate() {
        return update;
    }

    public void setUpdate(ArrayList<SendCheckOutResponse> update) {
        this.update = update;
    }

    public BasicSend(ArrayList<SendCheckOutResponse> update) {
        this.update = update;
    }

    ArrayList<SendCheckOutResponse>update;

}
