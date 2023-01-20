package com.example.demo.form;

public class loginForm {

	/** name */
	private String name;
	/** password */
	private String password;
	
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
	
	@Override
	public String toString() {
		return "loginForm [name=" + name + ", password=" + password + "]";
	}
}
