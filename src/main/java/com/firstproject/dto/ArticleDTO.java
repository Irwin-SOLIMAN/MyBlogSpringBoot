package com.firstproject.dto;

import com.firstproject.model.Article;
import com.firstproject.model.ArticleAuthor;
import com.firstproject.model.Author;
import com.firstproject.model.Image;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleDTO(
        Long id,
        String title,
        String content,
        LocalDateTime updatedAt,
        String categoryName,
        List<String> imageUrls,
        List<AuthorDTO> authors
) {

    public static ArticleDTO fromEntity(Article article) {

        return new ArticleDTO(
                        article.getId(),
                        article.getTitle(),
                        article.getContent(),
                        article.getUpdatedAt(),
                        article.getCategory() != null ? article.getCategory().getName() : "unknown",
                        article.getImages() != null ? article.getImages().stream().map(Image::getUrl).toList() : null,
                        article.getArticleAuthors() != null ? article.getArticleAuthors().stream().map((i) -> AuthorDTO.fromEntity(i.getAuthor())).toList() : null
                );
    }
}

