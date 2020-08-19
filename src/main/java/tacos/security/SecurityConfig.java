package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	/* HTTP 보안을 구성하는 메소드 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/design", "/orders")
			  .access("hasRole('ROLE_USER')")
			.antMatchers("/", "/**").access("permitAll")
		.and()
		 	.httpBasic()
		 ;
	} 
	
	/* 인메모리 기반 사용자 인증 정보를 구성하는 메소드 */
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("user1")
				.password("{noop}password1")
				.authorities("ROLE_USER")
				.and()
				.withUser("user2")
				.password("{noop}password2")
				.authorities("ROLE_USER")
				;
	}*/
	
	/* Jdbc 기반 사용자 인증 정보를 구성하는 메소드 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
			.dataSource(dataSource);
	}
}
