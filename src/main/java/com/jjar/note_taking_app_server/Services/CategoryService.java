package com.jjar.note_taking_app_server.Services;

import com.jjar.note_taking_app_server.Dtos.CategoryResponse;
import com.jjar.note_taking_app_server.Entities.Category;
import com.jjar.note_taking_app_server.Repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Category getOrCreateCategory(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            category = new Category();
            category.setName(name);
            category = categoryRepository.save(category);
        }
        return category;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .collect(Collectors.toList());
    }


    public CategoryResponse createCategory(CategoryResponse categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryResponse.class);
    }


    public Optional<CategoryResponse> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategoryResponse.class));
    }


    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
