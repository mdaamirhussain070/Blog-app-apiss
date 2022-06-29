package com.hussain.blog.services;

import java.util.List;

import com.hussain.blog.payloads.CategoryDto;

public interface CategoryService {
	
	//Create Category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//Update Category
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId); 
	
	//Delete Category
	void deleteCategory(Integer categoryId);
	
	//Get Single Category
	CategoryDto getSingleCategory(CategoryDto categoryDto,Integer categoryId);
	
	//Get All Category
	List<CategoryDto> getAllCategory();

}
