package com.pmh.fakestore.service;

import java.util.List;
import java.util.Optional;

import com.pmh.fakestore.entity.Product;

public interface ProductService {
	
	List<Product> allProducts();
	
	Optional<Product> getProductByID(Long id);
	
	List<Product> getProductByName(String name);
	
	void createProduct(Product product);
	
	void updateProduct(Product product);
}
