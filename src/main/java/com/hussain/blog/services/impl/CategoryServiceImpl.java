package com.hussain.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hussain.blog.entities.Category;
import com.hussain.blog.exceptions.ResourceNotFoundException;
import com.hussain.blog.payloads.CategoryDto;
import com.hussain.blog.repositories.CategoryRepo;
import com.hussain.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category=this.modelMapper.map(categoryDto,Category.class);
		Category savedCategory=this.categoryRepo.save(category);
		
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category1=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id",categoryId));
		
		category1.setCategoryTitle(categoryDto.getCategoryTitle());
		category1.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory=this.categoryRepo.save(category1);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category category1=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		this.categoryRepo.delete(category1);
	}

	@Override
	public CategoryDto getSingleCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category1=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		return this.modelMapper.map(category1, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		List category1=this.categoryRepo.findAll();
		List<CategoryDto> categoryDto=(List<CategoryDto>) category1.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categoryDto;
	}

}
