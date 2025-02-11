package com.firstproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthorContributionDTOCreate(
        @NotNull(message = "L'ID de l'auteur qui a contribué ne doit pas être nul")
        Long authorId,
        @NotBlank(message = "Contribution cannot be empty")
        String contribution
) {
}
