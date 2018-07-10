package com.kemal.spring.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kemal.spring.service.userDetails.UserDetailsServiceImpl;



@Configuration
@EnableWebSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	// Beans
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsServiceImpl);
		authProvider.setPasswordEncoder(bCryptPasswordEncoder);
		return authProvider;
	}

	// Override methods
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/index", "/", "/register", "/submit-registration")
				.permitAll().antMatchers("/adminPage/**").hasRole("ADMIN").anyRequest().authenticated().and()
				.formLogin().loginPage("/login").permitAll().loginProcessingUrl("/perform-login")
				.usernameParameter("email").passwordParameter("password").defaultSuccessUrl("/index", true)
				.failureUrl("/login-error.html").and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
				.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	

}
