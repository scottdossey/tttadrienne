package com.tts.techtalenttwitter.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//We are going to customize how authentication occurs.
		auth.jdbcAuthentication() //We want to authenticate using a Java Database Connection backend
			                      //IE--our authentication information is in a database.
			.usersByUsernameQuery(usersQuery) 
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource) //tells spring security what database to query (the standard one from H2)
			.passwordEncoder(bCryptPasswordEncoder);		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/console/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/custom.js").permitAll()
				.antMatchers("/custom.css").permitAll()
				.antMatchers().hasAuthority("USER").anyRequest().authenticated()
			.and()				
				.formLogin()
					.loginPage("/login").failureUrl("/login?error=true")
					.defaultSuccessUrl("/tweets")
					.usernameParameter("username")
					.passwordParameter("password")
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
			.and()
				.exceptionHandling()
			.and()
				.csrf().disable()
				.headers().frameOptions().disable();
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**",  
					     "/images/**");
	}
}
