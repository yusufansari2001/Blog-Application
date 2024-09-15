package com.springboot.blog.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;
	private String description;
	
	
	//one category will have many posts-so category is a parent of post
	//parent will have the three arguments shown below
	//the first one specifies how this class is mapped to post(we should have a parent dependency in child class
	//second is cacade type, if update/delete happens for parent class it will reflect to child also
	//third is orphanremoval, that means if child is not referring to any parent it will be erased by hibernate
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Category() {
		
	}
	public Category(long id, String name, String description, List<Post> posts) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.posts = posts;
	}
	
	
	

}
