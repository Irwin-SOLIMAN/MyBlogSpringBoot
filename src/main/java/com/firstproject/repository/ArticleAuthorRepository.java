package com.firstproject.repository;

import com.firstproject.model.ArticleAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleAuthorRepository  extends JpaRepository <ArticleAuthor, Long> {
}
