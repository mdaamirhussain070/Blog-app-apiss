package com.hussain.blog.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hussain.blog.payloads.ApiResponse;
import com.hussain.blog.payloads.CommentDto;
import com.hussain.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@PathVariable("postId") Integer postId,@RequestBody CommentDto CommentDto) {
		
		CommentDto commentDtos=this.commentService.createComment(CommentDto, postId);
		
		return new ResponseEntity<CommentDto>(commentDtos,HttpStatus.CREATED);
	}
}
