package com.yourname.myapp.service;

import com.yourname.myapp.repository.CategoryRepository;
import com.yourname.myapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

}
