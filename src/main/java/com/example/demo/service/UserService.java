//package com.example.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.domain.User;
//import com.example.demo.form.InsertUserForm;
//import com.example.demo.form.loginForm;
//import com.example.demo.repository.UesrRepository;
//
//@Service
//public class UserService implements UserDetailsService {
//
//	@Autowired
//	private UesrRepository userRepository;
//	
//	public void register(InsertUserForm form) {
//		User user = new User();
//		user.setName(form.getName());
//		user.setPassword(form.getPassword());//あとでハッシュ化も
//		user.setAuthority(2);
//		userRepository.insert(user);
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		return userRepository.findByMail(username);
//	}
//	
//	public boolean login(loginForm form) {
//	User user = userRepository.findByMail(form.getName());
//	if(user==null) {
//		return false;
//	}
//	if(user.getPassword().equals(form.getPassword())) {
//		return true;
//	}else {
//		return false;
//	}
//	
//	}
//}