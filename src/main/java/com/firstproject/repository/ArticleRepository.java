package com.firstproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.firstproject.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}