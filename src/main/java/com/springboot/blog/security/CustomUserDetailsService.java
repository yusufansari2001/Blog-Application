package com.springboot.blog.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;

//class to load user information from db for authentication
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}

	// its a method from UserDetailsService, which loads user data from db, which will be used by authenticate 
	//method of authenticationmanager to compare with user details from postman(request).
	//this method has an argument which will be passed by authenticate method
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
				()->new UsernameNotFoundException("user not found with username or email"+usernameOrEmail));
		
		Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
	}

}


