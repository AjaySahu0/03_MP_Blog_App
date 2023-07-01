package com.ait.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ait.binding.PostForm;
import com.ait.entity.Comment;
import com.ait.entity.Post;
import com.ait.repo.PostRepo;
import com.ait.service.BlogEnqService;

@Controller
public class BlogController {

	@Autowired
	private BlogEnqService blogEnqService;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String getDashboard(Model model) {

		List<Post> posts = blogEnqService.getPost();
		model.addAttribute("posts", posts);

		return "dashboard";
	}

	@GetMapping("/new-post")
	public String newPost(Model model) {

		model.addAttribute("user", new PostForm());

		return "new-post";
	}

	@GetMapping("/comment")
	public String commentPage(Model model) {
		List<Comment> comment = blogEnqService.getComment();
		model.addAttribute("comment", comment);

		return "comment";
	}

	@PostMapping("/save")
	public String handleRegister(@ModelAttribute("user") PostForm form, Model model) {

		System.out.println(form);
		boolean status = blogEnqService.postSave(form);

		if (status) {
			model.addAttribute("SucessMsg", "Post saved");
		} else {
			model.addAttribute("ErrorMsg", "Problem occured");
		}
		return "new-post";
	}

	/*
	 * @GetMapping("/delete") public String delete(@RequestParam("postId") Integer
	 * postId, Model model) { repo.deleteById(postId); model.addAttribute("msg",
	 * "product deleted"); model.addAttribute("products", repo.findAll()); return
	 * "data"; }
	 * 
	 */
	@GetMapping("/edit")
	public String edit(@RequestParam("postId") Integer postId, Model model) {

		Optional<Post> findById = postRepo.findById(postId);
		if (findById.isPresent()) {
			Post posts = findById.get();
			model.addAttribute("user", posts);
		}
		return "new-post";
	}

}
