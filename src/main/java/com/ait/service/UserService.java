package com.ait.service;

import java.util.List;

import com.ait.binding.CommentForm;
import com.ait.binding.LoginForm;
import com.ait.binding.RegistrationForm;
import com.ait.entity.Comment;
import com.ait.entity.Post;

public interface UserService {

	public boolean register(RegistrationForm form);

	public String login(LoginForm form);
	
	public Post insidePost(Integer postId);

	public List<Post> showAllPost();

	public boolean commentSave(CommentForm form , Integer postId);

	public List<Comment> getComment(Integer postId);
}
