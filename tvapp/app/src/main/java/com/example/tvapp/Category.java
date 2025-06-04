package com.example.tvapp;

import java.util.List;

public class Category {
    private final String name;
    private final List<SubCategory> subCategories;

    public Category(String name, List<SubCategory> subCategories) {
        this.name = name;
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }
}
