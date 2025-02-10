package com.firstproject.service;

import com.firstproject.dto.ArticleDTO;
import com.firstproject.exception.ResourceNotFoundException;
import com.firstproject.model.*;
import com.firstproject.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final AuthorRepository authorRepository;
    private final ArticleAuthorRepository articleAuthorRepository;

    public ArticleService(
            ArticleRepository articleRepository,
            CategoryRepository categoryRepository,
            ImageRepository imageRepository,
            AuthorRepository authorRepository,
            ArticleAuthorRepository articleAuthorRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.authorRepository = authorRepository;
        this.articleAuthorRepository = articleAuthorRepository;
    }

    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(ArticleDTO::fromEntity).collect(Collectors.toList());
    }

    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));
        return ArticleDTO.fromEntity(article);
    }

    public ArticleDTO createArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        // Gestion de la catégorie
        if (article.getCategory() != null) {
            Category category = categoryRepository.findById(article.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("La catégorie avec l'id " + article.getId() + " n'a pas été trouvé"));
            if (category == null) {
                return null;
            }
            article.setCategory(category);
        }

        // Gestion des images
        if (article.getImages() != null && !article.getImages().isEmpty()) {
            List<Image> validImages = new ArrayList<>();
            for (Image image : article.getImages()) {
                if (image.getId() != null) {
                    Image existingImage = imageRepository.findById(image.getId()).orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + image.getId() + " n'a pas été trouvé"));
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    } else {
                        return null;
                    }
                } else {
                    Image savedImage = imageRepository.save(image);
                    validImages.add(savedImage);
                }
            }
            article.setImages(validImages);
        }

        Article savedArticle = articleRepository.save(article);

        // Gestion des auteurs
        if (article.getArticleAuthors() != null) {
            for (ArticleAuthor articleAuthor : article.getArticleAuthors()) {
                Author author = articleAuthor.getAuthor();
                Author searchedAuthor = authorRepository.findById(author.getId()).orElseThrow(() -> new ResourceNotFoundException("L'auteur avec l'id " + author.getId() + " n'a pas été trouvé"));
                if (searchedAuthor == null) {
                    return null;
                }

                articleAuthor.setAuthor(searchedAuthor);
                articleAuthor.setArticle(savedArticle);
                articleAuthor.setContribution(articleAuthor.getContribution());

                articleAuthorRepository.save(articleAuthor);
            }
        }

        return ArticleDTO.fromEntity(savedArticle);
    }

    public ArticleDTO updateArticle(Long id, Article articleDetails) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));
        if (article == null) {
            return null;
        }
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setUpdatedAt(LocalDateTime.now());

        // Mise à jour de la catégorie
        if (articleDetails.getCategory() != null) {
            Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("La catégorie avec l'id " + id + " n'a pas été trouvé"));
            if (category == null) {
                return null;
            }
            article.setCategory(category);
        }

        // Mise à jour des images
        if (articleDetails.getImages() != null) {
            List<Image> validImages = new ArrayList<>();
            for (Image image : articleDetails.getImages()) {
                if (image.getId() != null) {
                    Image existingImage = imageRepository.findById(image.getId()).orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé"));
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    } else {
                        return null;
                    }
                } else {
                    Image savedImage = imageRepository.save(image);
                    validImages.add(savedImage);
                }
            }
            article.setImages(validImages);
        } else {
            article.getImages().clear();
        }

        // Mise à jour des auteurs
        if (articleDetails.getArticleAuthors() != null) {
            for (ArticleAuthor oldArticleAuthor : article.getArticleAuthors()) {
                articleAuthorRepository.delete(oldArticleAuthor);
            }

            List<ArticleAuthor> updatedArticleAuthors = new ArrayList<>();

            for (ArticleAuthor articleAuthorDetails : articleDetails.getArticleAuthors()) {
                Author author = articleAuthorDetails.getAuthor();
                author = authorRepository.findById(author.getId()).orElseThrow(() -> new ResourceNotFoundException("L'auteur avec l'id " + id + " n'a pas été trouvé"));
                if (author == null) {
                    return null;
                }

                ArticleAuthor newArticleAuthor = new ArticleAuthor();
                newArticleAuthor.setAuthor(author);
                newArticleAuthor.setArticle(article);
                newArticleAuthor.setContribution(articleAuthorDetails.getContribution());

                updatedArticleAuthors.add(newArticleAuthor);
            }

            for (ArticleAuthor articleAuthor : updatedArticleAuthors) {
                articleAuthorRepository.save(articleAuthor);
            }

            article.setArticleAuthors(updatedArticleAuthors);
        }

        Article updatedArticle = articleRepository.save(article);
        return ArticleDTO.fromEntity(updatedArticle);
    }

    public boolean deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));
        if (article == null) {
            return false;
        }

        articleAuthorRepository.deleteAll(article.getArticleAuthors());
        articleRepository.delete(article);
        return true;
    }
}