package com.pmh.fakestore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pmh.fakestore.jwt.AuthEntryPointJwt;
import com.pmh.fakestore.jwt.AuthTokenFilter;
import com.pmh.fakestore.userdetail.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		UserDetailsServiceImpl userDetailsServiceImpl;
		
		@Autowired
		private AuthEntryPointJwt unauthorizedHandler;
		
		@Bean
		public AuthTokenFilter authenticationJwtTokenFilter() {
			return new AuthTokenFilter();
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
		}
		
		@Bean
		@Override
		protected AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/api/auth/**","/","/swagger-resources/**","/swagger-ui.html", "/swagger-ui.html/**","/v2/api-docs", "/webjars/**").permitAll()
			.antMatchers("/api/user/**").hasRole("USER")
			.antMatchers("/api/admin/*").hasRole("ADMIN")
			.antMatchers("/api/test/**").permitAll()
			.anyRequest().authenticated();
			
			http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		}
}
