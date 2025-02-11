package com.firstproject.service;

import com.firstproject.dto.ArticleCreateDTO;
import com.firstproject.dto.ArticleDTO;
import com.firstproject.dto.AuthorContributionDTOCreate;
import com.firstproject.dto.ImageDTOCreate;
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

    // Front : Evoie un article semi complet (create)
    // Back : Vérifie que l'article envoyer corresond au minima requis (articleCreateDTO)
    //        si ok, alors conversion en article

    public ArticleDTO createArticle(ArticleCreateDTO articleCreateDTO) {

        Article article = ArticleCreateDTO.fromEntity(articleCreateDTO);

        // Gestion de la catégorie
        if (articleCreateDTO.categoryId() != null) {
            Category category = categoryRepository.findById(articleCreateDTO.categoryId()).orElseThrow(() -> new ResourceNotFoundException("La catégorie avec l'id " + article.getId() + " n'a pas été trouvé"));
            article.setCategory(category); // en récupérant l'objet catégorie de la BDD, cela permet à hibernate de faire le lien
        }

        // Gestion des images
        if (articleCreateDTO.images() != null) {
            List<Image> validImages = new ArrayList<>();
            for (ImageDTOCreate imageDTOCreate : articleCreateDTO.images()) {
                if (imageDTOCreate.id() != null) {
                    Image existingImage = imageRepository.findById(imageDTOCreate.id()).orElse(null);
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    } else {
                        throw new ResourceNotFoundException("L'image avec l'id " + imageDTOCreate.id() + " n'a pas été trouvé");
                    }
                } else {
                    Image imageToSave = ImageDTOCreate.fromEntity(imageDTOCreate);
                    Image savedImage = imageRepository.save(imageToSave);
                    validImages.add(savedImage);
                }
            }
            article.setImages(validImages); // possible car on a récupérer de la BDD l'objet ou il a été persisité juste avant
        }

        Article savedArticle = articleRepository.save(article);

        // Gestion des auteurs
        if (articleCreateDTO.authors() != null) {
            for (AuthorContributionDTOCreate authorContributionDTOCreate : articleCreateDTO.authors()) {

                Author searchedAuthor = authorRepository.findById(authorContributionDTOCreate.authorId()).orElseThrow(() -> new ResourceNotFoundException("L'auteur avec l'id " + authorContributionDTOCreate.authorId() + " n'a pas été trouvé"));

                ArticleAuthor articleAuthor = new ArticleAuthor();

                articleAuthor.setAuthor(searchedAuthor);
                articleAuthor.setArticle(savedArticle);
                articleAuthor.setContribution(authorContributionDTOCreate.contribution());

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