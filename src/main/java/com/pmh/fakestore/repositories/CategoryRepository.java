package com.pmh.fakestore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pmh.fakestore.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Boolean existsByName(String name);
}
