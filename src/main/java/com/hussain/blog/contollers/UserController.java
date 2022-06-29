package com.hussain.blog.contollers;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hussain.blog.payloads.ApiResponse;
import com.hussain.blog.payloads.UserDto;
import com.hussain.blog.services.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	//create user
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUserDto= userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//Update user Details
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		
		UserDto updatedUser =this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedUser);
	}
	
	// Delete User
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
		
		this.userService.deleteUser(userId);
		
		return new ResponseEntity(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
	// Get All Users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		return ResponseEntity.ok(this.userService.getAllUser());
	}
	
	//Get Users By Id 
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUsersById(@PathVariable Integer userId){
		
		UserDto userDto1=this.userService.getUserById(userId);
		return ResponseEntity.ok(userDto1);
	}

}
