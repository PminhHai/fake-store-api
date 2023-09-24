package com.pmh.fakestore.service;

import java.util.List;
import java.util.Optional;

import com.pmh.fakestore.entity.Category;

public interface CategoryService {
	
	List<Category> findAll();
	
	Optional<Category> getCategoryByID(Long id);
	
	void createCategory(Category category);
	
	void updateCategory(Category category);
}
