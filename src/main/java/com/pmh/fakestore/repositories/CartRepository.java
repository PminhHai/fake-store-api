package com.pmh.fakestore.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pmh.fakestore.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
	@Query(value = "select distinct * from carts where user_id = %:userId%;", nativeQuery = true)
	List<Cart> getCartByUserId(@Param("userId") Long userId);
	
	@Query(value = "select * from carts where id = :id", nativeQuery = true)
	Optional<Cart> findById(@Param("id") Long id);
}
