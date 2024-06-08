package com.pplbo.productservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "categoryid")
    private Long categoryId; 

    @Column(name = "categoryname")
    private String categoryName;

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
