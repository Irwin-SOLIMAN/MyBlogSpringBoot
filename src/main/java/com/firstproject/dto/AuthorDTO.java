package com.firstproject.dto;

import com.firstproject.model.Author;

public record AuthorDTO(Long id,
                        String firstname,
                        String lastname) {


    public static AuthorDTO fromEntity(Author author) {
        return new AuthorDTO(author.getId(), author.getFirstname(), author.getLastname());
    }

    ;

}
