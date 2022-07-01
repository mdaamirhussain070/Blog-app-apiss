package com.hussain.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import com.hussain.blog.repositories.PostRepo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hussain.blog.entities.Post;
import com.hussain.blog.exceptions.ResourceNotFoundException;
import com.hussain.blog.payloads.PostDto;
import com.hussain.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//File Name
		String name=file.getOriginalFilename();
		
		//Ramdom Name Generator File
		String ramdomID=UUID.randomUUID().toString();
		String fileName1=ramdomID.concat(name.substring(name.lastIndexOf(".")));
		
		//Full Path
		String filePath= path + File.separator + fileName1;
		
		
		//Create Folder if not created
		
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//file Copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath=path+File.separator+fileName;
		
		InputStream is=new FileInputStream(fullPath);
		
		return is;
	}
	
	public PostDto updatePostImage(PostDto postDto,Integer postId) {
		
		Post post=this.modelMapper.map(postDto,Post.class);
		
	//	Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		
		Post savepost = this.postRepo.save(post);
		PostDto postDtos=this.modelMapper.map(savepost,PostDto.class);
		return postDtos;
		
	}

}
