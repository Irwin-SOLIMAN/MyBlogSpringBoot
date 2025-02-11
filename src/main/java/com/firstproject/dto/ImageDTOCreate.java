package com.firstproject.dto;

import com.firstproject.model.Article;
import com.firstproject.model.Image;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record ImageDTOCreate(Long id,
                             @URL(message = "L'URL de l'image doit être valide")
                             String url
) {


    public static Image fromEntity(ImageDTOCreate imageDTOCreate) {
        Image image = new Image();
        image.setUrl(imageDTOCreate.url());
        return image;
    }


}
