package com.springboot.blog1.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "posts", uniqueConstraints = { @UniqueConstraint(columnNames = { "postTitle" }) })

public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "postTitle", nullable = false)
	private String postTitle;

	@Column(name = "postDescription", nullable = false)
	private String postDescription;

	@Column(name = "postContent", nullable = false)
	private String postContent;
	
	
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = false)
	Set<Comment> comments = new HashSet<>();

	/*
	 * we are making bidirectional relationship so we are defining hear OneTOMany
	 * The mappedBy attribute is used in JPA to establish bidirectional
	 * relationships between entities. It is typically used in conjunction with
	 * the @OneToMany or @OneToOne annotations to specify the inverse side of the
	 * relationship.
	 * 
	 * When you have a bidirectional relationship between two entities, one side is
	 * considered the owning side, and the other side is the inverse side. The
	 * mappedBy attribute is used on the inverse side to indicate the property in
	 * the owning side that maps to it.
	 * 
	 * In this example, the Post entity has a one-to-many relationship with the
	 * Comment entity, where one Post can have multiple Comment.
	 * 
	 * The @OneToMany annotation is used to define the relationship, and the
	 * mappedBy attribute specifies the inverse side of the relationship.
	 * 
	 * The cascade attribute is set to CascadeType.ALL, which means that any
	 * operation performed on the Department entity (such as persist, merge, remove,
	 * etc.) will be cascaded to the associated Employee entities. For example, if a
	 * department is deleted, all its associated employees will also be deleted
	 * automatically.
	 * 
	 * The orphanRemoval attribute is set to true, indicating that when an Employee
	 * entity is removed from the employees collection of a Department, it should be
	 * considered an orphan and automatically removed from the database.
	 * 
	 * With orphanRemoval enabled, if you remove an Employee entity from the
	 * employees collection of a Department and then persist or update the
	 * Department entity, the removed Employee entity will be deleted from the
	 * database.
	 */


}
