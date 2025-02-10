package com.firstproject.controller;

import com.firstproject.dto.ArticleDTO;
import com.firstproject.dto.AuthorDTO;
import com.firstproject.model.ArticleAuthor;
import com.firstproject.model.Author;
import com.firstproject.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository=authorRepository;
    }

    // Routter

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthor() {
        List<Author> authors = authorRepository.findAll();
        if(authors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<AuthorDTO> authorDTOS = authors.stream().map(AuthorDTO::fromEntity).toList();
        return  ResponseEntity.ok(authorDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if(author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AuthorDTO.fromEntity(author));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody Author author) {

            Author addedAuthor = authorRepository.save(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(AuthorDTO.fromEntity(addedAuthor));

    }

}
