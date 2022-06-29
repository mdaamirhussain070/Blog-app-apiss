package com.hussain.blog.repositories;





import org.springframework.data.jpa.repository.JpaRepository;

import com.hussain.blog.entities.Category;



public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
