package com.firstproject.service;

import com.firstproject.dto.AuthorDTO;
import com.firstproject.exception.ResourceNotFoundException;
import com.firstproject.model.Author;
import com.firstproject.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {


    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(AuthorDTO::fromEntity).toList();
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'auteur avec l'id " + id + " n'a pas été trouvé"));
        return AuthorDTO.fromEntity(author);
    }

    public AuthorDTO createAuthor(Author author) {
        Author addedAuthor = authorRepository.save(author);
        return AuthorDTO.fromEntity(author);
    }


}
