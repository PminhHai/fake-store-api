package com.pmh.fakestore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmh.fakestore.dto.CategoryDTO;
import com.pmh.fakestore.entity.Category;
import com.pmh.fakestore.repositories.CategoryRepository;
import com.pmh.fakestore.response.MessageResponse;
import com.pmh.fakestore.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping("/allCategory")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<CategoryDTO> allCategory(){
		List<Category> categories = categoryService.findAll();
		
		List<CategoryDTO> categoryDTOs = new ArrayList<>();
		
		for(int i = 0; i < categories.size();i++) {
			CategoryDTO categoryDTO = new CategoryDTO();
			
			categoryDTO.setId(categories.get(i).getId());
			categoryDTO.setName(categories.get(i).getName());
			
			categoryDTOs.add(categoryDTO);
		}
		
		return categoryDTOs;
	}
	
	@PostMapping("/addCategory")
	 @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
		if(categoryRepository.existsByName(categoryDTO.getName())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Category is already taken!"));
		}
		
		
		Category category = new Category();
		
		category.setName(categoryDTO.getName());
		
		categoryService.createCategory(category);
		
		return ResponseEntity.ok(new MessageResponse("Add Category successfully"));
	}
	
	@PutMapping("/updateCategory")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO) {
		Optional<Category> categoryOp = categoryRepository.findById(categoryDTO.getId());
		
		if(!categoryOp.isPresent()) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Category is not exist!"));
		}
		
		Category category = categoryOp.get();
		
		category.setName(categoryDTO.getName());
		
		categoryService.updateCategory(category);
		
		return ResponseEntity.ok(new MessageResponse("Update Category successfully"));
	}
}
