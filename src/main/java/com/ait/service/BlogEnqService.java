package com.ait.service;

import java.util.List;

import com.ait.binding.PostForm;
import com.ait.entity.Comment;
import com.ait.entity.Post;

public interface BlogEnqService {

	public boolean postSave(PostForm form);
	
	public List<Post> getPost();
	
	public List<Comment> getComment();

}
