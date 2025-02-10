package com.firstproject.dto;

import com.firstproject.model.Article;
import com.firstproject.model.ArticleAuthor;
import com.firstproject.model.Author;

public record ArticleAuthorDTO(Long id,
                               Article article,
                               Author author,
                               String contribution

) {


    public static ArticleAuthorDTO fromEntity(ArticleAuthor articleAuthor) {
        return new ArticleAuthorDTO(articleAuthor.getId(), articleAuthor.getArticle(), articleAuthor.getAuthor(), articleAuthor.getContribution());
    }


}
