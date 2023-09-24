package com.pmh.fakestore.service;

import java.util.List;

import com.pmh.fakestore.entity.Cart;

public interface CartService {
	
	List<Cart> getCartsByUserId(Long userId);
	
	void addCart(Cart cart);
	
	void updateCart(Cart cart);
	
	void deleteCartByID(Long id);
}
