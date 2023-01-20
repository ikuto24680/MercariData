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

/**
 * Userのログイン、ログアウト、登録に関するコントローラ.
 * @author pengi
 */
@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired
	private LoginUserDetailService userService;

	@RequestMapping("/")
	public String toLogin() {
		
		return "login";
	}
	
	/**
	 * ログインメソッド（ほぼSpring　Securityが行う）.
	 * @param form
	 * @return
	 */
	@RequestMapping("/login")
	public String login(loginForm form) {
//		if(!userService.login(form)){
//			System.out.println("false側");
//			return "login";
//		}
//		System.out.println("true側");
		
		return "redirect:/item/list";
	}
	
	/**
	 * ユーザー登録画面に遷移するメソッド.
	 * @return
	 */
	@GetMapping("/toRegister")
	public String register() {
		
		return "register";
	}
	
	/**
	 * ユーザーを登録するメソッド.
	 * @param form
	 * @param model
	 * @return
	 */
	@PostMapping("/register")
	public String register(InsertUserForm form,Model model) {
		userService.register(form,model);
		return "redirect:/";
	}
}