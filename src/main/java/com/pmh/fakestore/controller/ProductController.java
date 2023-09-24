package com.pmh.fakestore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmh.fakestore.dto.AddProductDTO;
import com.pmh.fakestore.dto.ProductDTO;
import com.pmh.fakestore.entity.Category;
import com.pmh.fakestore.entity.Product;
import com.pmh.fakestore.repositories.CategoryRepository;
import com.pmh.fakestore.repositories.ProductRepository;
import com.pmh.fakestore.response.MessageResponse;
import com.pmh.fakestore.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping("/allProduct")
	public List<ProductDTO> allProducts(){
		List<Product> products = productService.allProducts();
		
		List<ProductDTO> productDTOs = new ArrayList<>();
		
		for(int i = 0; i < products.size(); i++) {
			ProductDTO productDTO = new ProductDTO();
			
			productDTO.setId(products.get(i).getId());
			productDTO.setName(products.get(i).getName());
			productDTO.setDescription(products.get(i).getDescription());
			productDTO.setPrice(products.get(i).getPrice());
			productDTO.setAvaiableQuantity(products.get(i).getAvaiableQuantity());
			productDTO.setCategoryName(products.get(i).getCategory().getName());
			
			productDTOs.add(productDTO);
		}
		
		return productDTOs;
	}
	
	@GetMapping("/getProductByID/{id}")
	public ResponseEntity<?> getProductByID(@PathVariable Long id) {
		Optional<Product> productOp = productService.getProductByID(id);
		
		if(!productOp.isPresent()) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Product is not exists!"));
		}
		
		Product product = productOp.get();
		
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		productDTO.setAvaiableQuantity(product.getAvaiableQuantity());
		productDTO.setCategoryName(product.getCategory().getName());
		
		return ResponseEntity.ok(productDTO);
	}
	
	@GetMapping("/getProductByName")
	public ResponseEntity<?> getProductByName(@RequestParam String name) {
		List<Product> products = productService.getProductByName(name);
		
		if(products.size() == 0) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Product is not exists!"));
		}
		
		List<ProductDTO> productDTOs = new ArrayList<>();
		
		for(int i = 0; i < products.size(); i++) {
			ProductDTO productDTO = new ProductDTO();
			
			productDTO.setId(products.get(i).getId());
			productDTO.setName(products.get(i).getName());
			productDTO.setDescription(products.get(i).getDescription());
			productDTO.setPrice(products.get(i).getPrice());
			productDTO.setAvaiableQuantity(products.get(i).getAvaiableQuantity());
			productDTO.setCategoryName(products.get(i).getCategory().getName());
			
			productDTOs.add(productDTO);
		}
		
		return ResponseEntity.ok(productDTOs);
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@RequestBody AddProductDTO productDTO){
		Product product = new Product();
		
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setAvaiableQuantity(productDTO.getAvaiableQuantity());
		
		Optional<Category> category = categoryRepository.findById(productDTO.getCategoryId());
		
		Category category1 = category.get();
		product.setCategory(category1);
		
		productService.createProduct(product);
		
		return ResponseEntity.ok(new MessageResponse("Add Product Successfully !"));
	}
	
	@PutMapping("/updateProduct")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO){
		
		Optional<Product> productOp = productService.getProductByID(productDTO.getId());
		
		if(!productOp.isPresent()) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Product is not exists!"));
		}
		
		Product product = productOp.get();
		
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setAvaiableQuantity(productDTO.getAvaiableQuantity());
		
		product.setCategory(product.getCategory());
		
		productService.createProduct(product);
		
		return ResponseEntity.ok(new MessageResponse("Add Product Successfully !"));
	}
}
