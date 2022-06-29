package com.hussain.blog.services;

import java.util.List;

import com.hussain.blog.payloads.PostDto;

public interface PostService {
	
	//create post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//Update Post
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//Delete Post
	void deletePost(Integer postId);
	
	//Get All Post
	List<PostDto> getAllPost();
	
	//get postBy id
	PostDto getPostById(Integer postId);
	
	//get all posts by Category
	List<PostDto> getAllPostByCategory(Integer categoryId);
	
	//Get All Post By User
	List<PostDto> getAllPostByUser(Integer userId);
	
	//Search Post By keyword
	List<PostDto> searchPost(String keywaord);

}
