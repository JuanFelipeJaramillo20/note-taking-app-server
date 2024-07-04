package com.jjar.note_taking_app_server.Controllers;

import com.jjar.note_taking_app_server.Dtos.CategoryResponse;
import com.jjar.note_taking_app_server.Entities.Category;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import com.jjar.note_taking_app_server.Services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryResponse categoryDTO) {
        CategoryResponse createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }



    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Optional<CategoryResponse> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

