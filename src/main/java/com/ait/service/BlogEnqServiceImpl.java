package com.ait.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.binding.PostForm;
import com.ait.entity.Comment;
import com.ait.entity.Post;
import com.ait.entity.User;
import com.ait.repo.PostRepo;
import com.ait.repo.UserRepo;

@Service
public class BlogEnqServiceImpl implements BlogEnqService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private HttpSession session;

	@Override
	public boolean postSave(PostForm form) {

		Post postEntity = new Post();
		BeanUtils.copyProperties(form, postEntity);

		Integer userId = (Integer) session.getAttribute("userId");
		User userEntity = userRepo.findById(userId).get();
		postEntity.setUser(userEntity);

		// insert record
		postRepo.save(postEntity);
		return true;
	}

	@Override
	public List<Post> getPost() {

		Integer userId = (Integer) session.getAttribute("userId");

		/*
		 * Optional<User> findById = userRepo.findById(userId); if(findById.isPresent())
		 * { User userEntity = findById.get(); userEntity.get }
		 */
		/*
		 * List<Post> findByUserId = postRepo.findByUserId(userId); if(findByUserId.i)
		 */

		Optional<User> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			User userEntity = findById.get();
			List<Post> postFr = userEntity.getPostFr();
			return postFr;
		}

		return null;
	}

	@Override
	public List<Comment> getComment() {

		List<Comment> commentEntity = new ArrayList<>();
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<User> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			User userEntity = findById.get();
			List<Post> postFr = userEntity.getPostFr();
			for (Post post : postFr) {
				List<Comment> comment = post.getComment();
				for (Comment com : comment) {
					commentEntity.add(com);
				}
			}
		}
		return commentEntity;
	}
}
