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
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private LoginUserDetailService loginUserDetailService;
	
//	private UserDetailsService memberDetailService;

//	public void configure(WebSecurity web)throws Exception {
//		return (web) -> web.ignoring().antMatchers("/css", "/js");
//		web.ignoring().antMatchers("/css","/js");
//	}

//	protected void configure(HttpSecurity http) throws Exception {
//		
//		http.authorizeHttpRequests().antMatchers("/toRegister","/toLogin","/register","/search","/list","/detail","/toAdd","/add","/middle","/small","/toEdit","/edit").permitAll().anyRequest().authenticated();
//
//		http.formLogin().loginPage("/").loginProcessingUrl("/login").failureUrl("/?error=true")
//				.defaultSuccessUrl("/item/list", true).usernameParameter("name").passwordParameter("password");

//		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/toLogin")
//				.deleteCookies("JSESSIONID").invalidateHttpSession(true);
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		HttpSecurity HS ;
		http.formLogin(login -> login.loginProcessingUrl("/login").loginPage("/").defaultSuccessUrl("/item/list",true)
				.failureUrl("/?error").permitAll()).logout(logout -> logout.logoutSuccessUrl("/"))
				.authorizeHttpRequests(
						authz -> authz
//		                .mvcMatchers("/css/mercari").permitAll().mvcMatchers("/js/addItem").permitAll().mvcMatchers("/js/copy").permitAll()
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
								.mvcMatchers("/").permitAll().mvcMatchers("/login").permitAll()
								.mvcMatchers("/toRegister").permitAll().mvcMatchers("/register").permitAll()
								.mvcMatchers("/item/search").permitAll().mvcMatchers("/item/detail").permitAll()
								.mvcMatchers("/item/toAdd").permitAll().mvcMatchers("/item/add").permitAll()
//								.mvcMatchers("/../item/middle").permitAll().mvcMatchers("/./item/middle").permitAll()
//								.mvcMatchers("./item/middle").permitAll().mvcMatchers("./item/small").permitAll()
//								.mvcMatchers("http://localhost:8080/item/middle").permitAll()
//								.mvcMatchers("http://localhost:8080/item/small").permitAll()
								.mvcMatchers("/item/toEdit").permitAll().mvcMatchers("/item/edit").permitAll()
								.mvcMatchers("/item/list").permitAll().anyRequest().authenticated()).csrf(csrf -> csrf.disable());//.userDetailsService(userDetailService);//このへん
		return http.build();
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}