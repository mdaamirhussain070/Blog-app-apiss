package com.hussain.blog.services.impl;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hussain.blog.entities.User;
import com.hussain.blog.exceptions.ResourceNotFoundException;
import com.hussain.blog.payloads.UserDto;
import com.hussain.blog.repositories.UserRepo;
import com.hussain.blog.services.UserService;

import net.bytebuddy.asm.Advice.This;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user=this.userDtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		
		return this.userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser=this.userRepo.save(user);
		
		UserDto userDto1=this.userToUserDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		
		return  userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users=this.userRepo.findAll();
		
		List<UserDto> userDtos=users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		
		return   userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		this.userRepo.delete(user);

	}
	
	public User userDtoToUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto, User.class);
		
		
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
		
		return user;
	}
	
	public UserDto userToUserDto(User user) {
		
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
		
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		
		return userDto;
	}

}
