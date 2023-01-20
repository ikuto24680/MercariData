package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.demo.service.LoginUserDetailService;

//@Profile("production") // これ
/**
 * Spring Securityのコンフィグクラス.
 * @author pengi
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private LoginUserDetailService loginUserDetailService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login.loginProcessingUrl("/login").loginPage("/").defaultSuccessUrl("/item/list",true)
				.failureUrl("/?error").permitAll()).logout(logout -> logout.logoutSuccessUrl("/"))
				.authorizeHttpRequests(
						authz -> authz
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
								.mvcMatchers("/").permitAll().mvcMatchers("/login").permitAll()
								.mvcMatchers("/toRegister").permitAll().mvcMatchers("/register").permitAll()
								.mvcMatchers("/item/search").permitAll().mvcMatchers("/item/detail").permitAll()
								.mvcMatchers("/item/toAdd").permitAll().mvcMatchers("/item/add").permitAll()
								.mvcMatchers("/item/toEdit").permitAll().mvcMatchers("/item/edit").permitAll()
								.mvcMatchers("/item/list").permitAll().anyRequest().authenticated()).csrf(csrf -> csrf.disable());//.userDetailsService(userDetailService);//このへん
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}