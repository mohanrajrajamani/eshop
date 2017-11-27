package com.eshop.model;

import java.util.ArrayList;

/**
 * Created by Admin on 19-09-2017.
 */

public class Catagorey {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<SubCatagories> getSubCatagories() {
        return subCatagories;
    }

    public void setSubCatagories(ArrayList<SubCatagories> subCatagories) {
        this.subCatagories = subCatagories;
    }

    private String description;
    private ArrayList<SubCatagories>subCatagories;


}
