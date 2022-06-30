package com.hussain.blog.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.hussain.blog.services.FileService;

public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//File Name
		String name=file.getOriginalFilename();
		
		//Ramdom Name Generator File
		String ramdomID=UUID.randomUUID().toString();
		String fileName1=ramdomID.concat(name.substring(name.lastIndexOf(".")));
		
		//Full Path
		String filePath=File.separator+fileName1;
		
		
		//Create Folder if not created
		
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//file Copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		
		
		return name;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
