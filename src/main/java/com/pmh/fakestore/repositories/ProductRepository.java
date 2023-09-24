package com.pmh.fakestore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pmh.fakestore.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "select distinct * from products where `name` like %:name%;",nativeQuery = true)
	List<Product> findByName(@Param("name") String name);
}
