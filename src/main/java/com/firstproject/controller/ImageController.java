package com.firstproject.controller;

import com.firstproject.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.firstproject.dto.ImageDTO;
import com.firstproject.model.Image;


import java.util.List;


@RestController
@RequestMapping("/images")
public class ImageController {


    private final ImageService imageService;

    public ImageController(ImageService imageService) {

        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<ImageDTO> imagesDTO = imageService.getAllImages();
        if (imagesDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(imagesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        ImageDTO imageDTO = imageService.getImageById(id);
        if (imageDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageDTO);
    }

    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody Image image) {
        ImageDTO savedImage = imageService.createImage(image);
        return ResponseEntity.status(201).body(savedImage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody Image imageDetails) {
        ImageDTO imageDTO = imageService.updateImage(id, imageDetails);
        if (imageDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imageDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        boolean deleteImage = imageService.deleteImage(id);
        if (deleteImage) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
