package com.hussain.blog.services;

import com.hussain.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postId );
	
	void deleteComment(Integer commentId);

}
