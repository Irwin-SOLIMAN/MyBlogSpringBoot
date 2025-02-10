package com.firstproject.dto;

import com.firstproject.model.Article;
import com.firstproject.model.Image;

import java.util.List;

public record ImageDTO(Long id,
                       String url,
                       List<Long> articleIds

) {


    public static ImageDTO fromEntity(Image image) {
        return new ImageDTO(image.getId(), image.getUrl(), image.getArticles() != null ? image.getArticles().stream().map(Article::getId).toList() : null);
    }


}