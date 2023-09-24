package com.pmh.fakestore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmh.fakestore.dto.LoginDTO;
import com.pmh.fakestore.dto.SignUpDTO;
import com.pmh.fakestore.entity.Role;
import com.pmh.fakestore.entity.User;
import com.pmh.fakestore.jwt.JwtUtils;
import com.pmh.fakestore.repositories.RoleRepository;
import com.pmh.fakestore.repositories.UserRepository;
import com.pmh.fakestore.response.JwtResponse;
import com.pmh.fakestore.response.MessageResponse;
import com.pmh.fakestore.userdetail.UserDetailsImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO){
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt,
									userDetails.getId(),
									userDetails.getUsername(),
									userDetails.getEmail(),
									userDetails.getFullName(),
									roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDTO signupDTO) {
		if(userRepository.existsByUsername(signupDTO.getUsername())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if(userRepository.existsByEmail(signupDTO.getEmail())) {
			return ResponseEntity
			          .badRequest()
			          .body(new MessageResponse("Error: Email is already in use!"));
		}
		
		User user = new User();
		user.setUsername(signupDTO.getUsername());
		user.setEmail(signupDTO.getEmail());
		user.setFullName(signupDTO.getName());
		user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
		
		Set<String> strRoles = signupDTO.getRole();
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null) {
			Role userRole = roleRepository.findByName("ROLE_USER")
	    			.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	    	roles.add(userRole);
		} else {
	    	strRoles.forEach(role -> {
	            switch (role) {
	            case "admin":
	              Role adminRole = roleRepository.findByName("ROLE_ADMIN")
	                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	              roles.add(adminRole);

	              break;
	            default:
	              Role userRole = roleRepository.findByName("ROLE_USER")
	                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	              roles.add(userRole);
	            }
	          });
		}
	    
	    user.setRoles(roles);
	    userRepository.save(user);
	    
	    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
