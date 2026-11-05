package com.yourname.myapp.service;

import com.yourname.myapp.dto.CategoryRequest;
import com.yourname.myapp.dto.CategoryResponse;
import com.yourname.myapp.exception.ResourceNotFoundException;
import com.yourname.myapp.model.Categorie;
import com.yourname.myapp.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CategoryService(CategoryRepository categoryRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.categoryRepository = categoryRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        boolean exist = categoryRepository.existsByName(request.nom());

        if(!exist){
            throw new ResourceNotFoundException("Category not found");
        }

        Categorie categorie = new Categorie();
        categorie.setNom(request.nom());

        return CategoryResponse.from(categoryRepository.save(categorie));
    }

    public CategoryResponse getById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }


    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from).toList();
    }

    @Transactional
    public CategoryResponse modify(Long id, CategoryRequest request) {
        Categorie category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setNom(request.nom());

        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id){
        Categorie category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }

}
