package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

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
			List<LoginUser> user = repo.findByMail(username);
			if(CollectionUtils.isEmpty(user)) {
				throw new UsernameNotFoundException("user not found.");				
			}
			String password = user.get(0).getPassword();
			LoginUser loginUser = new LoginUser();
			loginUser.setName(username);
			loginUser.setPassword(password);
			return new LoginUserDetails(loginUser);
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
