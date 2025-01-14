package com.firstproject.controller;
import com.firstproject.dto.CategoryDTO;
import com.firstproject.model.Category;
import com.firstproject.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories= categoryRepository.findAll().stream().map(CategoryDTO::fromEntity).toList();
        if(categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category addedCategory = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCategory);
    }
}
