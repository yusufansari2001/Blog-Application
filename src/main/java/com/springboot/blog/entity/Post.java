package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(
		name="posts", uniqueConstraints= {@UniqueConstraint(columnNames= {"title"})}
)
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
	
	@Column(name="title",nullable=false)
    private String title;
	
	@Column(name="description",nullable=false)
    private String description;
	
	@Column(name="content",nullable=false)
    private String content;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment>comments=new HashSet<>();
	
	
	//Many posts will belong to a singl category
	// so this is a child of category
	//Child have  a parameter called fetch. Here it lazy it means when posts are loaded it will not load category
	//It will only load category if category methods are being used 
	@ManyToOne(fetch = FetchType.LAZY)
	//It has a foreign key from category
    @JoinColumn(name = "category_id")
    private Category category;
	

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Post() {
		
	}

	public Post(long id, String title, String description, String content,Set<Comment> comments,Category category) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.comments = comments;
		this.category=category;
	}

//	@Override
//	public String toString() {
//		return "Post [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content + "]";
//	}

	

}
