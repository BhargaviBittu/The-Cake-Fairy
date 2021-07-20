package com.sai.ecommerce.service;

import com.sai.ecommerce.model.Category;
import com.sai.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public void deleteCategoryById(int id){
        categoryRepository.deleteById(id);
    }

    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }
}
