package com.yourname.myapp.service;

import com.yourname.myapp.dto.CategoryRequest;
import com.yourname.myapp.dto.CategoryResponse;
import com.yourname.myapp.exception.ResourceNotFoundException;
import com.yourname.myapp.model.Categorie;
import com.yourname.myapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

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


    List<CategoryResponse> getAll()
    CategoryResponse modify(Long id, CategoryResponse request)
    void delete(Long id)

}
