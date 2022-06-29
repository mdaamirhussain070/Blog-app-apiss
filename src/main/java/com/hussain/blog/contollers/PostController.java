package com.hussain.blog.contollers;


import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.hussain.blog.entities.Post;
import com.hussain.blog.payloads.ApiResponse;
import com.hussain.blog.payloads.PostDto;
import com.hussain.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	//Create post
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId ){
		
		PostDto newpostDto=this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(newpostDto,HttpStatus.CREATED);
		
	}
	
	//Get Post By Id
	@GetMapping("post/{postId}/posts")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		
		PostDto postDtos=this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(postDtos,HttpStatus.OK);
	}
	
	//Get post By Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		
		List<PostDto> postDtos=this.postService.getAllPostByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	//Get Post By User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		
		List<PostDto> postDtos=this.postService.getAllPostByUser(userId);

		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	//Get All Post
	
	@GetMapping("/post/posts")
	public ResponseEntity<List <PostDto>> getAllPost(){
		
		List<PostDto> postDtos=this.postService.getAllPost();
		
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{postId}")
	public ApiResponse deletPost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post Deleted Successfully",true);
	}
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
}
