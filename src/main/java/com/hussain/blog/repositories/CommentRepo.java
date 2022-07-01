package com.hussain.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hussain.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
