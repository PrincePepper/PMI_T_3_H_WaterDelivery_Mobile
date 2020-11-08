package semen.sereda.waterapp;

import java.io.Serializable;

public class ProductClass implements Serializable {
    private String name;
    private Integer count;


    public ProductClass(String name, Integer count) {
        this.count = count;
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}