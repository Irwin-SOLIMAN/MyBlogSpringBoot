package com.firstproject.dto;

import com.firstproject.model.Article;
import com.firstproject.model.ArticleAuthor;
import com.firstproject.model.Author;

public class ArticleAuthorDTO {

        private Long id;
        private Article article;
        private Author author;
    private String contribution;

    public ArticleAuthorDTO (ArticleAuthor articleAuthor) {
        this.id = articleAuthor.getId();
        this.article = articleAuthor.getArticle();
        this.author = articleAuthor.getAuthor();
        this.contribution = articleAuthor.getContribution();
    }

    public static ArticleAuthorDTO fromEntity (ArticleAuthor articleAuthor) {
        return new ArticleAuthorDTO(articleAuthor);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }
}
