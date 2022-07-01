package com.hussain.blog.contollers;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hussain.blog.config.AppConstant;
import com.hussain.blog.entities.Post;
import com.hussain.blog.payloads.ApiResponse;
import com.hussain.blog.payloads.PostDto;
import com.hussain.blog.payloads.PostResponse;
import com.hussain.blog.services.FileService;
import com.hussain.blog.services.PostService;



@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
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
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			
			@RequestParam (value="pageNumber",defaultValue=AppConstant.PAGE_NUMBER ,required=false) Integer pageNumber,
			@RequestParam(value="pageSize" ,defaultValue=AppConstant.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstant.SORT_BY,required=false) String sortBy
			){
		
		PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
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
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keyword") String keywaord){
		
		List<PostDto> postDtos=this.postService.searchPost(keywaord);
		
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	//Post Image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile  image,
			@PathVariable("postId") Integer postId
			) throws IOException{
		
		PostDto postDtos=this.postService.getPostById(postId);
		
		String fileName=this.fileService.uploadImage(path, image);
		
		
		postDtos.setImageName(fileName);
		PostDto updatedPost=this.fileService.updatePostImage(postDtos, postId);
		
//		PostDto updatedPost=this.postService.updatePost(postDtos, postId);
		
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	@GetMapping(value="/post/{imageName}" , produces=MediaType.IMAGE_JPEG_VALUE )
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse httpResponse) throws IOException {
		
		InputStream resource=this.fileService.getResource(path, imageName);
		httpResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, httpResponse.getOutputStream());
	}
}
