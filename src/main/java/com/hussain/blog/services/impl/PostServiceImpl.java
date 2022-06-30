package com.hussain.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hussain.blog.entities.Category;
import com.hussain.blog.entities.Post;
import com.hussain.blog.entities.User;
import com.hussain.blog.exceptions.ResourceNotFoundException;
import com.hussain.blog.payloads.PostDto;
import com.hussain.blog.payloads.PostResponse;
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
	
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy){
		
		Pageable p=PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending());
		
		 
		Page<Post> pageposts=this.postRepo.findAll(p);
		
		List<Post> posts=pageposts.getContent();
		
		List<PostDto> postDtos=posts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pageposts.getNumber());
		postResponse.setPageSize(pageposts.getSize());
		postResponse.setTotalElement(pageposts.getTotalElements());
		postResponse.setTotalPages(pageposts.getTotalPages());
		postResponse.setLastPage(pageposts.isLast());
		
		return postResponse;
	}

	private Sort SortBy(String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

	private Sort sortBy(String sortBy) {
		// TODO Auto-generated method stub
		return null;
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
		
		List<Post> posts=this.postRepo.findByTitleContaining(keywaord);
		
		List<PostDto> postDtos=posts.stream().map((post)->this.modelmapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		
		return postDtos;
	}

}
