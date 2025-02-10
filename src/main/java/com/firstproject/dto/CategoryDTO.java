package com.firstproject.dto;

import com.firstproject.model.Category;

import java.util.List;

public record CategoryDTO(Long id,
                          String name,
                          List<ArticleDTO> articles) {


    // Method static de class
    public static CategoryDTO fromEntity(Category category) {

        return new CategoryDTO(category.getId(), category.getName(), category.getArticles() != null ? category.getArticles().stream().map(ArticleDTO::fromEntity).toList() : null);
    }


}
