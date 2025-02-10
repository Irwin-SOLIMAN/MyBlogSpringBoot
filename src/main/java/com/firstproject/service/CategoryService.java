package com.firstproject.service;

import com.firstproject.dto.CategoryDTO;
import com.firstproject.model.Category;
import com.firstproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categories = categoryRepository.findAll().stream().map(CategoryDTO::fromEntity).toList();
        return categories;
    }

    public CategoryDTO createCategory(Category category) {
        Category addedCategory = categoryRepository.save(category);
        return CategoryDTO.fromEntity(addedCategory);
    }

}
