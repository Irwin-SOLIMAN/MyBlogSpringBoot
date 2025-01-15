package com.firstproject.dto;

import com.firstproject.model.Article;
import com.firstproject.model.Image;

import java.util.List;

public class ImageDTO {
    private Long id;
    private String url;
    private List<Long> articleIds;


    public ImageDTO(Image image) {
        this.id = image.getId();
        this.url = image.getUrl();
        if(image.getArticles() != null) { this.articleIds = image.getArticles().stream().map(Article::getId).toList();
        }

    }

    public static ImageDTO fromEntity(Image image) {
        return new ImageDTO(image);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Long> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(List<Long> articleIds) {
        this.articleIds = articleIds;
    }

    // Getters et setters
}