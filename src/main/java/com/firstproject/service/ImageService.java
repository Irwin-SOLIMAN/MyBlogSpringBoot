package com.firstproject.service;

import com.firstproject.dto.ImageDTO;
import com.firstproject.exception.ResourceNotFoundException;
import com.firstproject.model.Image;
import com.firstproject.repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        List<ImageDTO> imagesDTO = images.stream().map(ImageDTO::fromEntity).toList();
        return imagesDTO;
    }

    public ImageDTO getImageById(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé"));
        return ImageDTO.fromEntity(image);
    }

    public ImageDTO createImage(Image image) {
        Image savedImage = imageRepository.save(image);
        return ImageDTO.fromEntity(savedImage);
    }

    public ImageDTO updateImage(Long id, Image imageDetails) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé"));
        if (image == null) {
            return null;
        }
        image.setUrl(imageDetails.getUrl());
        Image updatedImage = imageRepository.save(image);
        return ImageDTO.fromEntity(image);
    }

    public boolean deleteImage(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'image avec l'id " + id + " n'a pas été trouvé"));
        if (image == null) {
            return false;
        }
        imageRepository.delete(image);
        return true;
    }

}
