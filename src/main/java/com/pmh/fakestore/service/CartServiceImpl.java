package com.pmh.fakestore.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmh.fakestore.entity.Cart;
import com.pmh.fakestore.repositories.CartRepository;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Transactional
	@Override
	public void addCart(Cart cart) {
		cartRepository.save(cart);
		
	}
	
	@Transactional
	@Override
	public void updateCart(Cart cart) {
		cartRepository.save(cart);
		
	}
	
	@Transactional
	@Override
	public void deleteCartByID(Long id) {
		cartRepository.deleteById(id);
		
	}
	
	@Transactional
	@Override
	public List<Cart> getCartsByUserId(Long userId) {
		
		return cartRepository.getCartByUserId(userId);
	}

}
