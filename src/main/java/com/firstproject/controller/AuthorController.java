package com.firstproject.controller;


import com.firstproject.dto.AuthorDTO;

import com.firstproject.model.Author;
import com.firstproject.repository.AuthorRepository;
import com.firstproject.service.ArticleService;
import com.firstproject.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {


    private final AuthorService authorService;

    public AuthorController(ArticleService articleService, AuthorService authorService) {
        this.authorService = authorService;
    }

    // Routter

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthor() {

        List<AuthorDTO> authorDTOS = authorService.getAllAuthors();
        if (authorDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(authorDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO author = authorService.getAuthorById(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody Author author) {

        AuthorDTO addedAuthor = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAuthor);

    }

}
