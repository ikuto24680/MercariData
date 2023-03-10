package com.example.demo.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUser implements UserDetails {

	/** ID */
	private Integer id;
	/**  名前*/
	private String name;
	/**  パスワード*/
	private String password;
//	private User user;
	/** 権限 */
	private Integer authority;

//	public LoginUser(String name,String password, Integer id, Integer authority) {
//		user.setName(name);
//		user.setPassword(password);
//		this.id = id;
//		this.authority = authority;
//	}
//	
//	public LoginUser() {
//		
//	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "LoginUser [id=" + id + ", name=" + name + ", password=" + password + ", authority=" + authority + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public Collection<? extends GrantedAuthority> roleList() {
		// TODO Auto-generated method stub
		return null;
	}

}
