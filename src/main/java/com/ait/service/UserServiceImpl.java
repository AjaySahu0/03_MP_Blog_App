package com.ait.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.binding.CommentForm;
import com.ait.binding.LoginForm;
import com.ait.binding.RegistrationForm;
import com.ait.entity.Comment;
import com.ait.entity.Post;
import com.ait.entity.User;
import com.ait.repo.CommentRepo;
import com.ait.repo.PostRepo;
import com.ait.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo comRepo;

	@Autowired
	private HttpSession session;

	@Override
	public boolean register(RegistrationForm form) {
		// copy register form data to AshokitUserDtls
		User entity = new User();
		BeanUtils.copyProperties(form, entity);

		// insert record
		userRepo.save(entity);
		return true;
	}

	@Override
	public String login(LoginForm form) {
		User entity = userRepo.findByEmailAndPassword(form.getEmail(), form.getPassword());

		if (entity == null) {
			return "Invalid credentials";
		}

		// create session and user data in session
		session.setAttribute("userId", entity.getUserId());
		return "success";
	}

	@Override
	public List<Post> showAllPost() {

		List<Post> blogs = postRepo.findAll();

		return blogs;
	}

	@Override
	public Post insidePost(Integer postId) {

		Optional<Post> findById = postRepo.findById(postId);
		if (findById.isPresent()) {
			Post post = findById.get();
			return post;
		}
		return null;
	}

	@Override
	public boolean commentSave(CommentForm form, Integer postId) {

		Comment comEntity = new Comment();
		BeanUtils.copyProperties(form, comEntity);
		Post postEntity = postRepo.findById(postId).get();
		comEntity.setPost(postEntity);

		// insert data
		comRepo.save(comEntity);

		return true;
	}

	@Override
	public List<Comment> getComment(Integer postId) {

		Optional<Post> findById = postRepo.findById(postId);
		if (findById.isPresent()) {
			Post postEntity = findById.get();
			List<Comment> commentFr = postEntity.getComment();
			return commentFr;
		}
		return null;
	}

}
