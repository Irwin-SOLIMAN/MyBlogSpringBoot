package com.firstproject.dto;

import com.firstproject.model.Article;

import java.time.LocalDateTime;

public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private String categoryName;

    public ArticleDTO(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.updatedAt = article.getUpdatedAt();
        if(article.getCategory() != null) {
            this.categoryName = article.getCategory().getName();
        } else {
            this.categoryName = "unknown";
        }

    }

    // Method Static accessible sans instancier l'objet
    public static ArticleDTO fromEntity(Article article) {
        return new ArticleDTO(article);
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
