package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userDetailService;
	//DataSource dataSource;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	/* HTTP 보안을 구성하는 메소드 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/design", "/orders")
			 .access("hasRole('ROLE_USER')")
			 //.hasRole("USER")
			.antMatchers("/", "/**").access("permitAll")
			.antMatchers("/h2-console/*").permitAll()
		.and()
			//.httpBasic()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/design", true)
			//.loginProcessingUrl("/authenticate")
			//.usernameParameter("user")				// 이 메소드 없으면 default로 username이 필드 이름이 된다.
			//.passwordParameter("pwd")				// 이 메소드 없으면 default로 password가 필드 이름이 된다.
		.and()
			.logout()
			.logoutSuccessUrl("/")
		.and()
			.csrf()									// CSRF 공격 방어
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
		/*auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(
					"select username, password, enabled from users " +
					"where username=?"
					)
			.authoritiesByUsernameQuery(
					"select username, authority from authorities " +
					"where username=?")
			.passwordEncoder(new NoEncodingPasswordEncoder());
			*/
		
		auth.userDetailsService(userDetailService)
			.passwordEncoder(encoder());
	}
}
