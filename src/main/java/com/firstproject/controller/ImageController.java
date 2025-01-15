package com.firstproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.firstproject.dto.ImageDTO;
import com.firstproject.model.Image;
import com.firstproject.model.Article;
import com.firstproject.repository.ImageRepository;
import com.firstproject.repository.ArticleRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageRepository imageRepository;
    private final ArticleRepository articleRepository;

    public ImageController(ImageRepository imageRepository, ArticleRepository articleRepository) {
        this.imageRepository = imageRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<Image> images = imageRepository.findAll();
        if (images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ImageDTO> imageDTOs = images.stream()
                .map(ImageDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(imageDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ImageDTO.fromEntity(image));
    }

    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody Image image) {
        Image savedImage = imageRepository.save(image);
        return ResponseEntity.status(201).body(ImageDTO.fromEntity(image));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody Image imageDetails) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        image.setUrl(imageDetails.getUrl());
        Image updatedImage = imageRepository.save(image);
        return ResponseEntity.ok(ImageDTO.fromEntity(image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        imageRepository.delete(image);
        return ResponseEntity.noContent().build();
    }

}
