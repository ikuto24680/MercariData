package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.InsertUserForm;
import com.example.demo.form.loginForm;
import com.example.demo.service.LoginUserDetailService;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private LoginUserDetailService userService;

	@RequestMapping("/")
	public String toLogin() {
		
		return "login";
	}
	
	@RequestMapping("/login")
	public String login(loginForm form) {
//		if(!userService.login(form)){
//			System.out.println("false側");
//			return "login";
//		}
//		System.out.println("true側");
		
		return "redirect:/item/list";
	}
	
	@GetMapping("/toRegister")
	public String register() {
		
		return "register";
	}
	
	@PostMapping("/register")
	public String register(InsertUserForm form,Model model) {
		userService.register(form,model);
		return "redirect:/";
	}
}