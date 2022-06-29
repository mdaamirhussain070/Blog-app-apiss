package com.hussain.blog.contollers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hussain.blog.payloads.ApiResponse;
import com.hussain.blog.payloads.CategoryDto;
import com.hussain.blog.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired 
	private CategoryService categoryservice;
	
	//Create Category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategry(@Valid @RequestBody CategoryDto categoryDto){
		
	CategoryDto categoryDto1=	this.categoryservice.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(categoryDto1,HttpStatus.CREATED);
		
	}
	
	//Update Category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
			
		CategoryDto updatedcategoryDto=this.categoryservice.updateCategory(categoryDto, categoryId);
		
		return new ResponseEntity<CategoryDto>(updatedcategoryDto,HttpStatus.OK);
	}
	
	//Delete Category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		this.categoryservice.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully !!",true),HttpStatus.OK);
	}
	
	//Get Single Category
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		
		CategoryDto singleCategory=this.categoryservice.getSingleCategory(categoryDto, categoryId);
		
		return new ResponseEntity<CategoryDto>(singleCategory,HttpStatus.OK);
	}
	
	//Get All Category
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> categoryDto1=this.categoryservice.getAllCategory();
		return ResponseEntity.ok(categoryDto1);
	}
}
