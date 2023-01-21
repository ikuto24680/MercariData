package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.LoginUser;

@Repository
public class LoginUesrRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<LoginUser> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(LoginUser.class);

	public void insert(LoginUser user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "insert into users(name,password,authority) values(:name,:password,:authority);";
		template.update(sql, param);
	}
	
	public List<LoginUser> findByMail(String name) {
		String sql = "SELECT id, name,password,authority FROM users WHERE name=:name;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<LoginUser> user = template.query(sql, param, USER_ROW_MAPPER);
		return user;
	}
	
	public LoginUser findByMailAndPassword(String name, String password) {
		String sql = "SELECT id, name,password,authority FROM users WHERE name=:name AND password=:password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name).addValue("password", password);
		List<LoginUser> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
}