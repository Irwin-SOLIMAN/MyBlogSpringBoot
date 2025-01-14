package com.firstproject.model;
import java.util.List;

import com.firstproject.dto.ArticleDTO;
import jakarta.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Article> articles;

    // Getters

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

   // Setters

    public String setName(String name) {
        return this.name = name;
    }

     public List<Article> getArticles() {
         return articles;
     }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
