package com.pmh.fakestore.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmh.fakestore.entity.Category;
import com.pmh.fakestore.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Transactional
	@Override
	public List<Category> findAll() {
		
		return categoryRepository.findAll();
	}
	
	@Transactional
	@Override
	public Optional<Category> getCategoryByID(Long id) {
		
		return categoryRepository.findById(id);
	}
	
	@Transactional
	@Override
	public void createCategory(Category category) {
		categoryRepository.save(category);
		
	}
	
	@Transactional
	@Override
	public void updateCategory(Category category) {
		categoryRepository.save(category);
		
	}

}
