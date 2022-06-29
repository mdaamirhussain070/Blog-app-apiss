package com.hussain.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hussain.blog.entities.Category;
import com.hussain.blog.entities.Post;
import com.hussain.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
}

