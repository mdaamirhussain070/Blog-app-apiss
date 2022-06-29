package com.hussain.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hussain.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
