package com.firstproject.dto;

import com.firstproject.model.Category;

import java.util.List;

public class CategoryDTO {
    private Long id;
    private String name;
    private List<ArticleDTO> articles;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        if(category.getArticles() != null ) {
            this.articles = category.getArticles().stream().map(ArticleDTO::fromEntity).toList();
        }
    }

    // Method static de class 
    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(category);
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }
}
