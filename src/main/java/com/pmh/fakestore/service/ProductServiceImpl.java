package com.pmh.fakestore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmh.fakestore.entity.Product;
import com.pmh.fakestore.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	public List<Product> allProducts() {
		
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductByID(Long id) {
	
		return productRepository.findById(id);
	}

	@Override
	public List<Product> getProductByName(String name) {
		
		return productRepository.findByName(name);
	}

	@Override
	public void createProduct(Product product) {
		productRepository.save(product);
		
	}

	@Override
	public void updateProduct(Product product) {
		productRepository.save(product);
		
	}

}
