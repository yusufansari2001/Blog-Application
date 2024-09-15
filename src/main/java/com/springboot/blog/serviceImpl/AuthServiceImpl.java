package com.springboot.blog.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	
	

	public AuthServiceImpl(AuthenticationManager authenticationManager,
			               UserRepository userRepository,
			               RoleRepository roleRepository,
			               PasswordEncoder passwordEncoder,
			               JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager=authenticationManager;
		this.userRepository=userRepository;
		this.roleRepository=roleRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtTokenProvider=jwtTokenProvider;
	}
	
	@Override
	public String login(LoginDto loginDto) {
		
		
		// TODO Auto-generated method stub
		//this authenticate method will call loadUserByUsername method of UserDetailsService and pass userdetails
		//the loadUserByUsername will fetch the user details based on passed argument, if its not there it will throw 
		//exception
		//if its there both details are being compared
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(),loginDto.getPassword()));
		
		
		
		//storing the authentication object in securitycontext, so that userdetails is available throughout the
		//application for authentication process
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtTokenProvider.generateToken(authentication);
		
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {
		// TODO Auto-generated method stub
		
		//check whether the username already exists in db
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"username already exists");
		}
		
		//check whether the email already exists in db
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST,"email already exists");
		}
		
		User user=new User();
		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setUsername(registerDto.getUsername());
		
		Set<Role>roles=new HashSet<>();
		Role role=roleRepository.findByName("ROLE_USER").get();
		roles.add(role);
		
		user.setRoles(roles);
		userRepository.save(user);
		
		return "registered successfully";
	}

}
