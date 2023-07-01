package com.ait.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ait.entity.Post;

public interface PostRepo extends JpaRepository<Post, Integer> {

	public List<Post> findAll();
}
