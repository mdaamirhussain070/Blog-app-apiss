package com.hussain.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private int id;
	
	@NotEmpty(message="Please Enter Name !!")
	@Size(min=3, message="Name must be minimum of 3 Characters !!")
	private String name;
	
	@NotEmpty(message = "Please Enter Valid Email address !!")
	@Email(message="Please Enter Valid email !!")
	private String email;
	
	@NotEmpty(message="Please Enter Password !!")
	@Size(min=4,max=10, message="Password must be minimum of 4 Character and maximum of 10 Characters !! ")
	private String password;
	
	@NotEmpty(message="Plese Enter something about yourself !! ")
	private String about;
	

}
