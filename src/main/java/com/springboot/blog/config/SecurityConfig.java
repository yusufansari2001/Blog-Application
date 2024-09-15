package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableMethodSecurity
//for authentication in swagger
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {
	// to fetch the user
	private UserDetailsService userDetailsService;
	
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	private JwtAuthenticationFilter authenticationFilter;
	
	public SecurityConfig(UserDetailsService userDetailsService,JwtAuthenticationFilter authenticationFilter) {
		this.userDetailsService=userDetailsService;
		this.authenticationFilter=authenticationFilter;
	}
	
	//to encode the password
	//its a bean configuration, we can inject it anywhere in our project
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//its a bean configuration, we can inject it anywhere in our project
	//authentication manager uses userDetailsService and encoder, we dont have to to anything.
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	//whenevr we have any http requests, it goes through the filter first
	//The filter decides, which http request requires authentication and which requests doesnt require authentication
	// It also defines the type of authentication
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
		
		 http.csrf(csrf -> csrf.disable())
         .authorizeHttpRequests((authorize) ->
                 //authorize.anyRequest().authenticated()
                 authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                         //.requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                         .requestMatchers("/api/auth/**").permitAll()
                         .requestMatchers("/swagger-ui/**").permitAll()
                         .requestMatchers("/v3/api-docs/**").permitAll()
                         .anyRequest().authenticated()

         ).exceptionHandling( exception -> exception
                 .authenticationEntryPoint(authenticationEntryPoint)
         ).sessionManagement( session -> session
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails yusuf=User.builder()
//				.username("yusuf")
//				.password(passwordEncoder().encode("yusuf"))
//				.roles("USER")
//				.build();
//		UserDetails admin=User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(yusuf,admin);
//		
//	}

}
