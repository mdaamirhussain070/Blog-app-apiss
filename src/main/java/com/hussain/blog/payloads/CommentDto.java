package com.hussain.blog.payloads;

import com.hussain.blog.entities.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
	
	private int id;
	private String content;
//		private PostDto post;
//	
//	private UserDto userDto;

}
