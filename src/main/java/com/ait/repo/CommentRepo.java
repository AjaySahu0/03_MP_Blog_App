package com.ait.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
