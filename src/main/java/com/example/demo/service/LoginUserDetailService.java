package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.domain.LoginUser;
import com.example.demo.domain.LoginUserDetails;
import com.example.demo.form.InsertUserForm;
import com.example.demo.repository.LoginUesrRepository;

/**
 * Spring Security用クラス.
 * @author pengi
 */
@Service
public class LoginUserDetailService implements UserDetailsService {

	@Autowired
	private LoginUesrRepository repo;

	public LoginUserDetailService(LoginUesrRepository repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			LoginUser user = repo.findByMail(username);
			String password = user.getPassword();
			LoginUser loginUser = new LoginUser();
			loginUser.setName(username);
			loginUser.setPassword(password);
			return new LoginUserDetails(loginUser);
		} catch (Exception e) {
			throw new UsernameNotFoundException("user not found.", e);
		}
	}

	// signupメソッド（登録メソッドと同義）
	public void register(InsertUserForm form, Model model) {
		try {
			BCryptPasswordEncoder BCPE = new BCryptPasswordEncoder();
			LoginUser user = new LoginUser();
			user.setName(form.getName());
			user.setPassword(BCPE.encode(form.getPassword()));// あとでハッシュ化も
			user.setAuthority(2);
			repo.insert(user);
		} catch (Exception e) {
		}
	}

}
