package com.budgettracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import java.util.*;
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer categoryId;

    private String categoryName;

    private CategoryType categoryType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Transaction> transactions = new ArrayList<>();


    public Category() {
        super();
    }

    public Category(Integer categoryId, String categoryName, CategoryType categoryType) {
        super();
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        return Objects.equals(categoryId, other.categoryId) && Objects.equals(categoryName, other.categoryName);
    }

    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + "]";
    }

}
