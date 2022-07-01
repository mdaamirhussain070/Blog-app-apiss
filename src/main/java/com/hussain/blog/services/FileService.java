package com.hussain.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.hussain.blog.payloads.PostDto;

public interface FileService {
	
	String uploadImage(String path,MultipartFile file)throws IOException;
	
	InputStream getResource(String path,String fileName)throws FileNotFoundException;
	
	PostDto updatePostImage(PostDto postDto,Integer postId);
}
