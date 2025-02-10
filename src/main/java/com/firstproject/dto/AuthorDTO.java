package com.firstproject.dto;

import com.firstproject.model.Author;

public class AuthorDTO {

        private Long id;
        private String firstname;
        private String lastname;

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.firstname = author.getFirstname();
        this.lastname = author.getLastname();
    }

    public static AuthorDTO fromEntity (Author author) {
        return new AuthorDTO(author);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
