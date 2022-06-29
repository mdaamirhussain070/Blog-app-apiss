package com.hussain.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hussain.blog.entities.Category;
import com.hussain.blog.entities.Post;
import com.hussain.blog.entities.User;
import com.hussain.blog.exceptions.ResourceNotFoundException;
import com.hussain.blog.payloads.PostDto;
import com.hussain.blog.repositories.CategoryRepo;
import com.hussain.blog.repositories.PostRepo;
import com.hussain.blog.repositories.UserRepo;
import com.hussain.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id", userId));
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post=this.modelmapper.map(postDto,Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost=this.postRepo.save(post);
		
		return this.modelmapper.map(newPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		
		Post savepost = this.postRepo.save(post);
		PostDto postDtos=this.modelmapper.map(savepost,PostDto.class);
		return postDtos;
	} 

	@Override
	public void deletePost(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		this.postRepo.delete(post);
	}
	
	public List<PostDto> getAllPost(){
		
		List<Post> posts=this.postRepo.findAll();
		
		List<PostDto> postDtos=posts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id", postId));
		
		PostDto postDtos=this.modelmapper.map(post, PostDto.class);
		
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
		
		List<Post> posts=this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override    
	public List<PostDto> getAllPostByUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		
		List<Post> posts=this.postRepo.findByUser(user);
		
		List<PostDto> postDtos=posts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keywaord) {
		// TODO Auto-generated method stub
		return null;
	}

}
