package com.hussain.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hussain.blog.entities.Comment;
import com.hussain.blog.entities.Post;
import com.hussain.blog.exceptions.ResourceNotFoundException;
import com.hussain.blog.payloads.CommentDto;
import com.hussain.blog.repositories.CommentRepo;
import com.hussain.blog.repositories.PostRepo;
import com.hussain.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
	
		comment.setPost(post);
//		comment.setPosts(post);
//		comment.setUsers(null);
//		comment.setContent(commentDto.getContent());
//		comment.setUser(post.getUser());
		
		Comment savedComment=this.commentRepo.save(comment);
	//	this.commentRepo.s
		CommentDto commentDtos=this.modelMapper.map(savedComment, CommentDto.class);
		return commentDtos;
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub

	}

}
