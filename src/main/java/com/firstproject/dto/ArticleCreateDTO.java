package com.firstproject.dto;

import com.firstproject.model.Article;
import com.firstproject.model.Image;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

// ce DTO permet de checker un ensemble de champs requis.
public record ArticleCreateDTO(
        @NotBlank(message = "Le titre ne doit pas être vide")
        @Size(min = 2, max = 50, message = "Le titre doit contenir entre 2 et 50 caractères")
        String title,
        @NotBlank(message = "Le contenu ne doit pas être vide")
        @Size(min = 10, message = "Le contenu doit contenir au moins 10 caractères")
        String content,
        @NotNull(message = "L'ID de la catégorie ne doit pas être nul")
        @Positive(message = "L'ID de la catégorie doit être un nombre positif")
        Long categoryId,
        @NotEmpty(message = "Les images ne doivent pas être vide")
        List<@Valid ImageDTOCreate> images,
        @NotEmpty(message = "La liste des auteurs ne doit pas être vide")
        List<@Valid AuthorContributionDTOCreate> authors
) {

    public static Article fromEntity(ArticleCreateDTO articleCreateDTO) {

        // on return un article partiel qui va être compléter au fur et a mesure dans le service pour les imbrications complexes (catégories,...)

        Article article = new Article();
        article.setContent(articleCreateDTO.content());
        article.setTitle(articleCreateDTO.title());
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        return article;

    }
}

