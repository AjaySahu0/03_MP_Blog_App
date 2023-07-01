package com.ait.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ait.binding.CommentForm;
import com.ait.binding.LoginForm;
import com.ait.binding.RegistrationForm;
import com.ait.entity.Comment;
import com.ait.entity.Post;
import com.ait.service.UserService;

@Controller
public class IndexController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String getIndexPage() {
		return "index";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("login") LoginForm form, Model model) {

		System.out.println(form);

		String status = userService.login(form);
		if (status.contains("success")) {

			// redirect req to dashboard method
			return "redirect:/dashboard";
		}
		model.addAttribute("errorMsg", status);

		return "login";
	}

	@GetMapping("/login")
	public String getLoginPage(Model model) {

		model.addAttribute("user", new LoginForm());
		return "login";
	}

	@PostMapping("/register")
	public String handleRegister(@Validated @ModelAttribute("user") RegistrationForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "register";
		}
		System.out.println(form);
		boolean status = userService.register(form);

		if (status) {
			model.addAttribute("SucessMsg", "Account Registered");
		} else {
			model.addAttribute("ErrorMsg", "Problem occured");
		}
		return "register";
	}

	@GetMapping("/register")
	public String getRegisterPage(Model model) {

		model.addAttribute("user", new RegistrationForm());
		return "register";
	}

	@GetMapping("/allpost")
	public String getAllblogs(Model model) {

		List<Post> blogs = userService.showAllPost();
		model.addAttribute("blogs", blogs);
		return "allpost";
	}

	@GetMapping("/inside-post")
	public String getInsidePost(@RequestParam("postId") Integer postId, Model model) {
		//System.out.println(postId);

		Post post = userService.insidePost(postId);
		model.addAttribute("post", post);
		model.addAttribute("user", new CommentForm());
		
		List<Comment> comment = userService.getComment(postId);
		model.addAttribute("comment", comment);

		return "inside-post";
	}

	@PostMapping("/save-comment")
	public String handleRegister(@ModelAttribute("user") CommentForm form,@RequestParam("postId") Integer postId, Model model) {

		System.out.println(form);
		System.out.println(postId);
		
		Post post = userService.insidePost(postId);
		model.addAttribute("post", post);
		
		List<Comment> comment = userService.getComment(postId);
		model.addAttribute("comment", comment);
		
		boolean status = userService.commentSave(form , postId);

		if (status) {
			model.addAttribute("SucessMsg", "Comment saved");
		} else {
			model.addAttribute("ErrorMsg", "Problem occured");
		}
		return "inside-post";
	}

}
