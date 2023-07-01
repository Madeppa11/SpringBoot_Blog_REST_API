package com.springboot.blog1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String body;
	
	/*creating rellationship between Post and comment table
	 * hear ManyToOne means many comments belongs to One post
	 * and fetchType.LAZY means it will helps to fetch related entity from the database when we use relationship.
	 * */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_Id",nullable = false)   
	
	private Post post;  
	/*The name attribute of @JoinColumn specifies the name of the foreign key column in the Comment table 
	 * that references the post table.
	 In this case, the foreign key column is named "post_Id".
	 */
}
