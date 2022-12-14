package com.coderscampus.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	// $2a$10$UnGJmjoS35B0GL3zO429T.nUIVInOsasMIaJ49wR/lDmRfDpsYcOG
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
// 		.withUser("sup123")
//  	.password("dude")
//  	.roles("USER", "ADMIN");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
	    .antMatchers("/dashboard").hasAnyRole("USER")
	    .antMatchers("/users/").hasAnyRole("USER")
	    .antMatchers("/task/**").hasAnyRole("USER")
	    .antMatchers("/createComment").hasAnyRole("USER")
	    .antMatchers("/profile").hasAnyRole("USER")
	    .antMatchers("/comment/**").hasAnyRole("USER")
	    .antMatchers("/register").anonymous()
		.and()
	    .formLogin()
	    .loginPage("/login")
		.defaultSuccessUrl("/dashboard")
		.permitAll();
		
	}
	
	
	
}
