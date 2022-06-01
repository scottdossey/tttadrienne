package com.tts.techtalenttwitter.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//We need to tell Spring Boot that we want to store this in a 
//Database....so we add the @Entity tag which tells the 
//JPA (Java Persistence API) that this is a class we want to have
//a database for
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserProfile {		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	@Email(message = "Please provide a valid email")
	@NotEmpty(message = "Please provide an email")
	private String email;
	
	@Length(min = 3, message = "Your username must have at least 3 characters")
	@Length(max = 15, message = "Your username cannot have more than 15 characters")
	@Pattern(regexp="[^\\s]+", message="Your username cannot contain spaces")
	private String username;
		
	@Length(min = 5, message = "Your password must have at least 5 characters")
	private String password;
	
	@NotEmpty(message = "Please provide your first name")
	private String firstName;
	
	@NotEmpty(message = "Please provide your last name")
	private String lastName;
	
	
	//We are going to create a many to many mapping....
	//But what will users be related to? .....
	//Other Users!!!!  We will have a many to many 
	//mapping of UserProfile to UserProfile....
	
	
	//A a User has many followers........
	//And a User has many people they are following....

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_follower", joinColumns = @JoinColumn(name="user_id"),
	           inverseJoinColumns = @JoinColumn(name="follower_id"))
	private List<UserProfile> followers;
	
	
	@ManyToMany(mappedBy="followers")
	private List<UserProfile> following;
			
	private int active;
	
	@CreationTimestamp
	private Date createdAt;
	
	
	//Database has columns
	// user_id, email, ....., active, createdAt
	
	//But roles is not going to be stored in a column.
	//Because we cannot store a collection of things in 
	//a database column.

	//Instead roles will be associated with User through 
	//a database relationship...
	
	//We are going to make this relationship be a many to many
	//relationship between roles and users..
	
	//IE a user can have many roles.
	//  and each role can have many users associated.
	//In order to have a many to many relationship in a database
	//we create a separate table that lists what roles and users 
	//are associated with each other.
	//Table: user_role
    //Columns:  user_id, role_id
	//             1,    1    #user_id 1 has role_id 1
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id"),
	           inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles;
		
}
