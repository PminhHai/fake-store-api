package com.pmh.fakestore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pmh.fakestore.dto.AddCartDTO;
import com.pmh.fakestore.dto.UpdateCartDTO;
import com.pmh.fakestore.entity.Cart;
import com.pmh.fakestore.entity.Product;
import com.pmh.fakestore.entity.User;
import com.pmh.fakestore.repositories.CartRepository;
import com.pmh.fakestore.repositories.UserRepository;
import com.pmh.fakestore.response.MessageResponse;
import com.pmh.fakestore.service.CartService;
import com.pmh.fakestore.service.ProductService;
import com.pmh.fakestore.userdetail.UserDetailsImpl;

@Repository
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserRepository userRepository;
	
	
	@PostMapping("/addCart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addCart(@RequestBody AddCartDTO cartDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		Cart cart = new Cart();
		
		cart.setProductId(cartDTO.getProductId());
		cart.setQuantity(cartDTO.getQuantity());
		
		Optional<Product> productOp = productService.getProductByID(cartDTO.getProductId());
		
		Product product = productOp.get();
		cart.setAmount(cartDTO.getQuantity() * product.getPrice());
		
		Optional<User> userOp = userRepository.findById(userDetails.getId());
		
		User user = userOp.get();
		
		cart.setUser(user);
		
		cartService.addCart(cart);
		
		return ResponseEntity.ok(new MessageResponse("Add Cart Successfully"));
	}
	
	
	@PutMapping("/updateCart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateCart(@RequestBody UpdateCartDTO cartDTO) {
		
		Optional<Cart> cartOp = cartRepository.findById(cartDTO.getId());			
		
		System.out.println(cartOp);
		
		Cart cart = cartOp.get();
		
		System.out.println(cart);
		
		cart.setQuantity(cartDTO.getQuantity());
		
		cartRepository.save(cart);
		
		return ResponseEntity.ok(new MessageResponse("Update Cart Successfully"));
	}
	
	@DeleteMapping("/deleteCart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteCart(@RequestParam Long id) {
		Optional<Cart> cartOp = cartRepository.findById(id);
		
		if(!cartOp.isPresent()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Cart does not exist !"));
		}
		
		Cart cart = cartOp.get();
		
		cartService.deleteCartByID(cart.getId());
		
		return ResponseEntity.ok(new MessageResponse("Delete Car Successfully !"));
	}
	
	
}
